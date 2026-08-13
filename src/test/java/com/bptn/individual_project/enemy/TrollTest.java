package com.bptn.individual_project.enemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.bptn.individual_project.character.Warrior;

/**
 * Test suite for Troll enemy class.
 * Tests Troll-specific behavior including regeneration special attack.
 */
public class TrollTest {
    
    private Troll troll;
    private Warrior target;
    
    @BeforeEach
    public void setUp() {
        troll = new Troll("Troll");
        target = new Warrior("Hero", 100, 20, null);
    }
    
    /**
     * Test: Troll initializes with correct stats.
     */
    @Test
    public void testTrollInitialization() {
        assertEquals("Troll", troll.getName());
        assertEquals(100, troll.getMaxHealth());
        assertEquals(100, troll.getHealth());
        assertEquals(14, troll.getAttackPower());
    }
    
    /**
     * Test: Troll attack deals damage.
     */
    @Test
    public void testTrollAttack() {
        int targetHealthBefore = target.getHealth();
        troll.attack(target);
        
        assertEquals(targetHealthBefore - troll.getAttackPower(), target.getHealth());
    }
    
    /**
     * Test: Troll special attack heals instead of dealing damage.
     * This is an unconventional special attack that doesn't harm the target.
     */
    @Test
    public void testTrollSpecialAttackHeals() {
        // Damage the troll first
        troll.takeDamage(60); // Health = 40
        int initialHealth = troll.getHealth();
        
        // Special attack should heal
        troll.specialAttack(target);
        
        int expectedHealth = 90; // 40 + 50
        assertEquals(expectedHealth, troll.getHealth());
    }
    
    /**
     * Test: Troll special attack healing is clamped to max health.
     */
    @Test
    public void testTrollSpecialAttackHealingClamped() {
        troll.takeDamage(30); // Health = 70
        troll.specialAttack(target);
        
        // Should be clamped to 100 (max health)
        assertEquals(100, troll.getHealth());
    }
    
    /**
     * Test: Troll rewards are correct.
     */
    @Test
    public void testTrollRewards() {
        assertEquals(25, troll.getGoldReward());
        assertEquals(35, troll.getXpReward());
    }
    
    
    /**
     * Test: Troll displays its stats correctly.
     */
    @Test
    public void testTrollDisplayStats() {
        // Capture console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            troll.displayStats();

            String stats = output.toString();

            assertTrue(stats.contains("Name: Troll"));
            assertTrue(stats.contains("Health: 100/100"));
            assertTrue(stats.contains("Attack Power: 14"));
        } finally {
            // Restore console output
            System.setOut(originalOut);
        }
    }
}
