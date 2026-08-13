package com.bptn.individual_project.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test suite for Warrior character class.
 * Tests class-specific attack implementations and stats.
 */
public class WarriorTest {
    
    private Warrior warrior;
    private Warrior target;
    
    @BeforeEach
    public void setUp() {
        warrior = new Warrior("WarriorA", 120, 15, null);
        target = new Warrior("WarriorB", 100, 10, null);
    }
    
    /**
     * Test: Warrior initializes with correct base stats.
     */
    @Test
    public void testWarriorInitialization() {
        assertEquals("WarriorA", warrior.getName());
        assertEquals(120, warrior.getMaxHealth());
        assertEquals(15, warrior.getAttackPower());
    }
    
    /**
     * Test: Warrior attack deals damage to target.
     * Tests that attack() method calls takeDamage() on target.
     */
    @Test
    public void testWarriorAttack() {
        int targetHealthBefore = target.getHealth();
        warrior.attack(target);
        
        // Target should have taken damage
        assertEquals(targetHealthBefore - warrior.getAttackPower(), target.getHealth());
    }
    
    /**
     * Test: Warrior special attack deals double damage.
     * Tests that specialAttack() deals 2x attack power.
     */
    @Test
    public void testWarriorSpecialAttack() {
        int targetHealthBefore = target.getHealth();
        warrior.specialAttack(target);
        
        // Special attack should deal double damage
        assertEquals(targetHealthBefore - (warrior.getAttackPower() * 2), target.getHealth());
    }
    
    /**
     * Test: Warrior can kill target with attack.
     */
    @Test
    public void testWarriorAttackKill() {
        Warrior weakTarget = new Warrior("Weak", 15, 5, null);
        warrior.attack(weakTarget);
        
        assertTrue(weakTarget.getHealth() <= 0);
        assertFalse(weakTarget.isAlive());
    }
    
    /**
     * Test: Warrior can kill target with special attack.
     */
    @Test
    public void testWarriorSpecialAttackKill() {
        Warrior weakTarget = new Warrior("Weak", 30, 5, null);
        warrior.specialAttack(weakTarget);
        
        assertTrue(weakTarget.getHealth() <= 0);
        assertFalse(weakTarget.isAlive());
    }
    
    /**
     * Test: Warrior displays inherited stats and class-specific stats.
     */
    @Test
    public void testDisplayStats() {
        // Capture System.out output.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            // Call the overridden displayStats() method.
            warrior.displayStats();

            String result = output.toString();

            // Verify inherited stats from the parent class.
            assertTrue(result.contains("Name: WarriorA"));
            assertTrue(result.contains("Level: "));
            assertTrue(result.contains("Race: "));
            assertTrue(result.contains("Health: "));
            assertTrue(result.contains("Attack Power: 15"));
            assertTrue(result.contains("Gold: "));
            assertTrue(result.contains("XP: "));

            // Verify Warrior-specific output.
            assertTrue(result.contains("Class: Warrior"));

        } finally {
            // Restore the original System.out.
            System.setOut(originalOut);
        }
    }
}
