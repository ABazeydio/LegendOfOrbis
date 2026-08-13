package com.bptn.individual_project.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test suite for Mage character class.
 * Tests class-specific attack implementations and stats.
 */
public class MageTest {
    
    private Mage mage;
    private Mage target;
    
    @BeforeEach
    public void setUp() {
        mage = new Mage("MageA", 80, 18, null);
        target = new Mage("MageB", 75, 16, null);
    }
    
    /**
     * Test: Mage initializes with correct base stats.
     */
    @Test
    public void testMageInitialization() {
        assertEquals("MageA", mage.getName());
        assertEquals(80, mage.getMaxHealth());
        assertEquals(18, mage.getAttackPower());
    }
    
    /**
     * Test: Mage attack deals damage to target.
     */
    @Test
    public void testMageAttack() {
        int targetHealthBefore = target.getHealth();
        mage.attack(target);
        
        assertEquals(targetHealthBefore - mage.getAttackPower(), target.getHealth());
    }
    
    /**
     * Test: Mage special attack deals double damage.
     */
    @Test
    public void testMageSpecialAttack() {
        int targetHealthBefore = target.getHealth();
        mage.specialAttack(target);
        
        assertEquals(targetHealthBefore - (mage.getAttackPower() * 2), target.getHealth());
    }
    
    /**
     * Test: Mage can kill target with attack.
     */
    @Test
    public void testMageAttackKill() {
        Mage weakTarget = new Mage("Weak", 15, 5, null);
        mage.attack(weakTarget);
        
        assertTrue(weakTarget.getHealth() <= 0);
        assertFalse(weakTarget.isAlive());
    }
    
    /**
     * Test: Mage can kill target with special attack.
     */
    @Test
    public void testMageSpecialAttackKill() {
        Mage weakTarget = new Mage("Weak", 30, 5, null);
        mage.specialAttack(weakTarget);
        
        assertTrue(weakTarget.getHealth() <= 0);
        assertFalse(weakTarget.isAlive());
    }
    
    /**
     * Test: Mage displays inherited stats and class-specific stats.
     */
    @Test
    public void testDisplayStats() {
        // Capture System.out output.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            // Call the overridden displayStats() method.
            mage.displayStats();

            String result = output.toString();

            // Verify inherited stats from the parent class.
            assertTrue(result.contains("Name: MageA"));
            assertTrue(result.contains("Level: "));
            assertTrue(result.contains("Race: "));
            assertTrue(result.contains("Health: "));
            assertTrue(result.contains("Attack Power: 18"));
            assertTrue(result.contains("Gold: "));
            assertTrue(result.contains("XP: "));

            // Verify Mage-specific output.
            assertTrue(result.contains("Class: Mage"));

        } finally {
            // Restore the original System.out.
            System.setOut(originalOut);
        }
    }
}
