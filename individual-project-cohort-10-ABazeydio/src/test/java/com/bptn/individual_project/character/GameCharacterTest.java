package com.bptn.individual_project.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for GameCharacter abstract class.
 * Tests core character mechanics including health, XP/leveling, gold, equipment bonuses,
 * and damage handling. Uses Warrior as concrete implementation.
 * 
 * Complex Methods Tested:
 * 1. earnExperience() - Tests XP accumulation, multiple level-ups in one call, and state management
 * 2. applyEquipmentBonus() - Tests complex health/attack bonus logic with various state combinations
 */
public class GameCharacterTest {
    
    private Warrior character;
    
    @BeforeEach
    public void setUp() {
        // Create a warrior for testing (concrete implementation of abstract GameCharacter)
        character = new Warrior("TestHero", 100, 20, null);
    }
    
    
    /**
     * Test: Character initializes with correct base stats when no race is provided.
     * Verifies that null race doesn't break initialization.
     */
    @Test
    public void testConstructorWithoutRace() {
        Warrior warrior = new Warrior("Hero", 80, 15, null);
        assertEquals("Hero", warrior.getName());
        assertEquals(80, warrior.getHealth());
        assertEquals(80, warrior.getMaxHealth());
        assertEquals(15, warrior.getAttackPower());
        assertEquals(1, warrior.getLevel());
        assertEquals(0, warrior.getExperience());
        assertEquals(0, warrior.getGold());
    }
    
    /**
     * Test: Character initializes with race buffs applied to health and attack power.
     * Verifies that DEMONIC race (+20/+20) buffs are correctly added to base stats.
     */
    @Test
    public void testConstructorWithDemonicRace() {
        Warrior warrior = new Warrior("DemonicHero", 100, 20, Race.DEMONIC);
        assertEquals(120, warrior.getMaxHealth()); // 100 + 20 buff
        assertEquals(120, warrior.getHealth()); // starts at full health
        assertEquals(40, warrior.getAttackPower()); // 20 + 20 buff
    }
    
    /**
     * Test: Character initializes with ANGELIC race buffs (+30/+30).
     */
    @Test
    public void testConstructorWithAngelicRace() {
        Warrior warrior = new Warrior("AngelicHero", 100, 20, Race.ANGELIC);
        assertEquals(130, warrior.getMaxHealth());
        assertEquals(50, warrior.getAttackPower()); // 20 + 30 ANGELIC buff
    }
    
    /**
     * Test: Character initializes with HUMAN race (no buffs).
     */
    @Test
    public void testConstructorWithHumanRace() {
        Warrior warrior = new Warrior("HumanHero", 100, 20, Race.HUMAN);
        assertEquals(100, warrior.getMaxHealth());
        assertEquals(20, warrior.getAttackPower());
    }
    
    
    /**
     * Test: Adding positive gold amount increases total.
     */
    @Test
    public void testAddGoldPositive() {
        character.addGold(50);
        assertEquals(50, character.getGold());
        character.addGold(25);
        assertEquals(75, character.getGold());
    }
    
    /**
     * Test: Attempting to add zero gold has no effect.
     */
    @Test
    public void testAddGoldZero() {
        character.addGold(0);
        assertEquals(0, character.getGold());
    }
    
    /**
     * Test: Attempting to add negative gold is ignored (safety check).
     */
    @Test
    public void testAddGoldNegative() {
        character.addGold(100);
        character.addGold(-50);
        assertEquals(100, character.getGold()); // Should remain 100, not become 50
    }
    
    /**
     * Test: Spending gold reduces total when sufficient funds available.
     */
    @Test
    public void testSpendGoldSuccess() {
        character.addGold(100);
        boolean result = character.spendGold(40);
        assertTrue(result);
        assertEquals(60, character.getGold());
    }
    
    /**
     * Test: Spending more gold than available returns false and doesn't deduct.
     */
    @Test
    public void testSpendGoldInsufficientFunds() {
        character.addGold(30);
        boolean result = character.spendGold(50);
        assertFalse(result);
        assertEquals(30, character.getGold()); // Amount unchanged
    }
    
    /**
     * Test: Spending exactly all available gold succeeds.
     */
    @Test
    public void testSpendGoldExactAmount() {
        character.addGold(75);
        boolean result = character.spendGold(75);
        assertTrue(result);
        assertEquals(0, character.getGold());
    }
    
    /**
     * Test: Attempting to spend zero or negative gold returns false.
     */
    @Test
    public void testSpendGoldZeroOrNegative() {
        character.addGold(50);
        assertFalse(character.spendGold(0));
        assertFalse(character.spendGold(-10));
        assertEquals(50, character.getGold()); // Unchanged
    }
    
    
    /**
     * Test: takeDamage reduces health by correct amount.
     */
    @Test
    public void testTakeDamage() {
        character.takeDamage(30);
        assertEquals(70, character.getHealth());
    }
    
    /**
     * Test: takeDamage does not allow health to drop below zero.
     */
    @Test
    public void testTakeDamageExcessive() {
        character.takeDamage(150); // More than max health
        assertEquals(0, character.getHealth());
    }
    
    /**
     * Test: isAlive returns true when health > 0.
     */
    @Test
    public void testIsAliveTrue() {
        assertTrue(character.isAlive());
        character.takeDamage(99);
        assertTrue(character.isAlive()); // Still 1 HP
    }
    
    /**
     * Test: isAlive returns false when health reaches 0.
     */
    @Test
    public void testIsAliveFalse() {
        character.takeDamage(100);
        assertFalse(character.isAlive());
    }
    
    /**
     * Test: heal increases health without exceeding max health.
     */
    @Test
    public void testHealPartial() {
        character.takeDamage(50);
        int result = character.heal(30);
        assertEquals(80, result);
        assertEquals(80, character.getHealth());
    }
    
    /**
     * Test: heal clamps health to maxHealth when heal would exceed it.
     */
    @Test
    public void testHealExceedsMax() {
        character.takeDamage(50);
        int result = character.heal(100); // Would be 150, but clamped to 100
        assertEquals(100, result);
        assertEquals(100, character.getHealth());
    }
    
    /**
     * Test: heal on full health returns max health unchanged.
     */
    @Test
    public void testHealAlreadyFull() {
        int result = character.heal(50);
        assertEquals(100, result);
        assertEquals(100, character.getHealth());
    }

    
    /**
     * COMPLEX TEST: earnExperience with no level-up.
     * Tests basic XP accumulation below the level-up threshold (100 XP).
     */
    @Test
    public void testEarnExperienceNoLevelUp() {
        character.earnExperience(50);
        assertEquals(50, character.getExperience());
        assertEquals(1, character.getLevel());
    }
    
    /**
     * COMPLEX TEST: earnExperience triggers single level-up.
     * Tests XP exceeding threshold, triggering levelUp() and resetting XP.
     * Verifies level increase, stat boost, and health restoration.
     */
    @Test
    public void testEarnExperienceSingleLevelUp() {
        // Initial state: level 1, health 100, attack 20
        character.earnExperience(100);
        
        // After level-up:
        assertEquals(2, character.getLevel());
        assertEquals(0, character.getExperience()); // XP resets after reaching 100
        assertEquals(110, character.getMaxHealth()); // +10 max health
        assertEquals(110, character.getHealth()); // Health restored to new max
        assertEquals(30, character.getAttackPower()); // +10 attack
    }
    
    /**
     * COMPLEX TEST: earnExperience triggers multiple level-ups.
     * Tests complex scenario where single XP gain causes multiple level-ups.
     * For example: earning 250 XP from level 1 should:
     * - First 100 XP → Level 2 (100 XP needed)
     * - Next 100 XP → Level 3 (100 XP needed)
     * - Remaining 50 XP → stays in experience pool
     */
    @Test
    public void testEarnExperienceMultipleLevelUps() {
        // Earn 250 XP at once
        character.earnExperience(250);
        
        // Should have leveled up twice (2 * 100 = 200 XP spent), with 50 remaining
        assertEquals(3, character.getLevel());
        assertEquals(50, character.getExperience());
        
        // Each level-up adds 10 to max health: 100 + 10 + 10 = 120
        assertEquals(120, character.getMaxHealth());
        assertEquals(120, character.getHealth());
        
        // Each level-up adds 10 to attack: 20 + 10 + 10 = 40
        assertEquals(40, character.getAttackPower());
    }
    
    /**
     * COMPLEX TEST: earnExperience with exact level-up boundary.
     * Tests earning exactly 100 XP (exactly one level).
     */
    @Test
    public void testEarnExperienceExactLevelUp() {
        character.earnExperience(100);
        assertEquals(2, character.getLevel());
        assertEquals(0, character.getExperience());
    }
    
    /**
     * COMPLEX TEST: earnExperience accumulates before level-up.
     * Tests gradual XP gain: 50 -> 60 -> 50 (total 160 triggers one level-up).
     */
    @Test
    public void testEarnExperienceAccumulation() {
        character.earnExperience(50);
        assertEquals(50, character.getExperience());
        assertEquals(1, character.getLevel());
        
        character.earnExperience(60);
        assertEquals(10, character.getExperience()); // 110 - 100 after level-up
        assertEquals(2, character.getLevel());
    }
    
    /**
     * Test: Level-up updates stats in correct order.
     */
    @Test
    public void testLevelUpStatsOrder() {
        character.earnExperience(100);
        // Verify all stats increased
        assertEquals(2, character.getLevel());
        assertEquals(110, character.getMaxHealth());
        assertEquals(30, character.getAttackPower());
    }
    
    
    /**
     * COMPLEX TEST: applyEquipmentBonus with positive weapon bonus.
     * Tests adding attack power while at full health.
     * Verifies attack increases but health remains unchanged.
     */
    @Test
    public void testApplyEquipmentBonusWeaponAtFullHealth() {
        // Initial: health=100/100, attack=20
        character.applyEquipmentBonus(10, 0); // +10 attack
        
        assertEquals(100, character.getHealth());
        assertEquals(100, character.getMaxHealth());
        assertEquals(30, character.getAttackPower());
    }
    
    /**
     * COMPLEX TEST: applyEquipmentBonus with armor bonus while at full health.
     * Tests adding max health while at full health.
     * Verifies current health increases along with max health.
     */
    @Test
    public void testApplyEquipmentBonusArmorAtFullHealth() {
        // Initial: health=100/100, attack=20
        // At full health, increasing max health should also increase current health
        character.applyEquipmentBonus(0, 20); // +20 armor
        
        assertEquals(120, character.getMaxHealth());
        assertEquals(120, character.getHealth()); // Should increase with max health
    }
    
    /**
     * COMPLEX TEST: applyEquipmentBonus with armor while not at full health.
     * Tests adding armor when player has taken damage.
     * Verifies current health stays below new max (doesn't auto-heal).
     */
    @Test
    public void testApplyEquipmentBonusArmorBelowFullHealth() {
        character.takeDamage(40); // health = 60/100
        
        character.applyEquipmentBonus(0, 20); // +20 armor
        
        assertEquals(120, character.getMaxHealth());
        assertEquals(60, character.getHealth()); // Stays at 60, not healed
    }
    
    /**
     * COMPLEX TEST: applyEquipmentBonus with both weapon and armor.
     * Tests combined bonuses applied in one call.
     */
    @Test
    public void testApplyEquipmentBonusBoth() {
        character.applyEquipmentBonus(10, 15); // +10 attack, +15 armor
        
        assertEquals(30, character.getAttackPower());
        assertEquals(115, character.getMaxHealth());
        assertEquals(115, character.getHealth()); // At full health, so increases
    }
    
    /**
     * COMPLEX TEST: applyEquipmentBonus with negative values (unequipping).
     * Tests removing bonuses when unequipping gear.
     */
    @Test
    public void testApplyEquipmentBonusNegative() {
        // First apply bonus
        character.applyEquipmentBonus(10, 20);
        assertEquals(30, character.getAttackPower());
        assertEquals(120, character.getMaxHealth());
        
        // Now remove it
        character.applyEquipmentBonus(-10, -20);
        assertEquals(20, character.getAttackPower());
        assertEquals(100, character.getMaxHealth());
        assertEquals(100, character.getHealth());
    }
    
    /**
     * COMPLEX TEST: applyEquipmentBonus clamps health when max health decreases.
     * Tests removing armor when current health exceeds new max.
     * This can happen in edge cases when swapping equipment.
     */
    @Test
    public void testApplyEquipmentBonusHealthClamping() {
        // Start at full health
        character.applyEquipmentBonus(0, 50); // Max health becomes 150
        assertEquals(150, character.getHealth());
        
        // Remove bonus that would make max health lower than current
        character.applyEquipmentBonus(0, -60); // Max health becomes 90
        
        // Current health should be clamped to new max
        assertEquals(90, character.getMaxHealth());
        assertEquals(90, character.getHealth()); // Clamped down
    }
    
    /**
     * COMPLEX TEST: applyEquipmentBonus with partial health loss.
     * Tests the specific case where player is partially damaged and armor is added.
     */
    @Test
    public void testApplyEquipmentBonusComplexHealthScenario() {
        character.takeDamage(30); // health = 70/100
        character.applyEquipmentBonus(0, 30); // Add armor, max health becomes 130
        
        assertEquals(130, character.getMaxHealth());
        assertEquals(70, character.getHealth()); // Still 70, not affected by max health increase
    }
    
    
    /**
     * Test: All getters return correct initial values.
     */
    @Test
    public void testGetters() {
        assertEquals("TestHero", character.getName());
        assertEquals(1, character.getLevel());
        assertEquals(100, character.getHealth());
        assertEquals(100, character.getMaxHealth());
        assertEquals(20, character.getAttackPower());
        assertEquals(0, character.getExperience());
        assertEquals(0, character.getGold());
    }
}
