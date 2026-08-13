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
 * Test suite for Goblin enemy class.
 */
public class GoblinTest {
    
    private Goblin goblin;
    private Warrior target;
    
    @BeforeEach
    public void setUp() {
        goblin = new Goblin("Goblin");
        target = new Warrior("Hero", 100, 20, null);
    }
    
    /**
     * Test: Goblin initializes with correct stats.
     */
    @Test
    public void testGoblinInitialization() {
        assertEquals("Goblin", goblin.getName());
        assertEquals(50, goblin.getMaxHealth());
        assertEquals(50, goblin.getHealth());
        assertEquals(20, goblin.getAttackPower());
    }
    
    /**
     * Test: Goblin attack deals damage.
     */
    @Test
    public void testGoblinAttack() {
        int targetHealthBefore = target.getHealth();
        goblin.attack(target);
        
        assertEquals(targetHealthBefore - goblin.getAttackPower(), target.getHealth());
    }
    
    /**
     * Test: Goblin special attack deals base attack + 5.
     */
    @Test
    public void testGoblinSpecialAttack() {
        int targetHealthBefore = target.getHealth();
        goblin.specialAttack(target);
        
        int expectedDamage = goblin.getAttackPower() + 5;
        assertEquals(targetHealthBefore - expectedDamage, target.getHealth());
    }
    
    /**
     * Test: Goblin rewards are correct.
     */
    @Test
    public void testGoblinRewards() {
        assertEquals(10, goblin.getGoldReward());
        assertEquals(15, goblin.getXpReward());
    }
    
    
    /**
     * Test: Goblin displays its stats correctly.
     */
    @Test
    public void testGoblinDisplayStats() {
        // Capture console output
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            goblin.displayStats();

            String stats = output.toString();

            assertTrue(stats.contains("Name: Goblin"));
            assertTrue(stats.contains("Health: 50/50"));
            assertTrue(stats.contains("Attack Power: 20"));

        } finally {
            // Restore console output
            System.setOut(originalOut);
        }
    }
}
