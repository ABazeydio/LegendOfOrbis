package com.bptn.individual_project.enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bptn.individual_project.character.Warrior;

/**
 * Test suite for DragonBoss enemy class.
 * Tests Dragon-specific high-damage stats and armor-piercing special attack.
 */
public class DragonBossTest {
    
    private DragonBoss dragon;
    private Warrior target;
    
    @BeforeEach
    public void setUp() {
        dragon = new DragonBoss("Dragon");
        target = new Warrior("Hero", 100, 20, null);
    }
    
    /**
     * Test: DragonBoss initializes with correct stats including DEMONIC race.
     */
    @Test
    public void testDragonBossInitialization() {
        assertEquals("Dragon", dragon.getName());
        assertEquals(170, dragon.getMaxHealth()); // 150 + 20 DEMONIC buff
        assertEquals(170, dragon.getHealth());
        assertEquals(45, dragon.getAttackPower()); // 25 + 20 DEMONIC buff
    }
    
    /**
     * Test: DragonBoss has DEMONIC race applied.
     */
    @Test
    public void testDragonBossDemonicRace() {
        DragonBoss dragonBoss = new DragonBoss("TestDragon");
        assertEquals(170, dragonBoss.getMaxHealth()); // 150 + 20 from DEMONIC
        assertEquals(45, dragonBoss.getAttackPower()); // 25 + 20 from DEMONIC
    }
    
    /**
     * Test: DragonBoss attack deals damage.
     */
    @Test
    public void testDragonBossAttack() {
        int targetHealthBefore = target.getHealth();
        dragon.attack(target);
        
        assertEquals(targetHealthBefore - dragon.getAttackPower(), target.getHealth());
    }
    
    /**
     * Test: DragonBoss special attack (Armor-Piercing Strike) deals extra damage.
     * Special attack deals base attack + 15.
     */
    @Test
    public void testDragonBossSpecialAttack() {
        int targetHealthBefore = target.getHealth();
        dragon.specialAttack(target);
        
        int expectedDamage = dragon.getAttackPower() + 15;
        assertEquals(targetHealthBefore - expectedDamage, target.getHealth());
    }
    
    
    /**
     * Test: DragonBoss rewards are correct (gold and XP).
     */
    @Test
    public void testDragonBossRewards() {
        assertEquals(100, dragon.getGoldReward());
        assertEquals(200, dragon.getXpReward());
    }
    
    
    /**
     * Test: DragonBoss displays its stats correctly.
     */
    @Test
    public void testDragonBossDisplayStats() {
        // Capture console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            dragon.displayStats();

            String stats = output.toString();

            assertTrue(stats.contains("Name: Dragon"));
            assertTrue(stats.contains("Health: 170/170"));
            assertTrue(stats.contains("Attack Power: 45"));
            assertTrue(stats.contains("Race: Demonic"));
        } finally {
            // Restore console output
            System.setOut(originalOut);
        }
    }
}
