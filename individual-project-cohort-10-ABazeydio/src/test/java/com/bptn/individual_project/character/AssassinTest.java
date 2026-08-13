package com.bptn.individual_project.character;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for Assassin character class.
 * Tests class-specific attack implementations and stats.
 */
public class AssassinTest {

    private Assassin assassin;
    private Assassin target;

    @BeforeEach
    public void setUp() {
        assassin = new Assassin("AssassinA", 100, 15, null);
        target = new Assassin("AssassinB", 100, 15, null);
    }

    /**
     * Test: Assassin initializes with correct base stats.
     */
    @Test
    public void testAssassinInitialization() {
        assertEquals("AssassinA", assassin.getName());
        assertEquals(100, assassin.getMaxHealth());
        assertEquals(15, assassin.getAttackPower());
    }

    /**
     * Test: Assassin attack deals damage to target.
     */
    @Test
    public void testAssassinAttack() {
        int targetHealthBefore = target.getHealth();
        assassin.attack(target);

        assertEquals(targetHealthBefore - assassin.getAttackPower(), target.getHealth());
    }

    /**
     * Test: Assassin special attack deals double damage.
     */
    @Test
    public void testAssassinSpecialAttack() {
        int targetHealthBefore = target.getHealth();
        assassin.specialAttack(target);

        assertEquals(targetHealthBefore - (assassin.getAttackPower() * 2), target.getHealth());
    }

    /**
     * Test: Assassin can kill target with attack.
     */
    @Test
    public void testAssassinAttackKill() {
        Assassin weakTarget = new Assassin("Weak", 15, 5, null);
        assassin.attack(weakTarget);

        assertTrue(weakTarget.getHealth() <= 0);
        assertFalse(weakTarget.isAlive());
    }

    /**
     * Test: Assassin can kill target with special attack.
     */
    @Test
    public void testAssassinSpecialAttackKill() {
        Assassin weakTarget = new Assassin("Weak", 30, 5, null);
        assassin.specialAttack(weakTarget);

        assertTrue(weakTarget.getHealth() <= 0);
        assertFalse(weakTarget.isAlive());
    }

    /**
     * Test: Assassin displays inherited stats and class-specific stats.
     */
    @Test
    public void testDisplayStats() {
        // Capture System.out output.
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(output));

        try {
            // Call the overridden displayStats() method.
            assassin.displayStats();

            String result = output.toString();

            // Verify inherited stats from the parent class.
            assertTrue(result.contains("Name: AssassinA"));
            assertTrue(result.contains("Level: "));
            assertTrue(result.contains("Race: "));
            assertTrue(result.contains("Health: "));
            assertTrue(result.contains("Attack Power: 15"));
            assertTrue(result.contains("Gold: "));
            assertTrue(result.contains("XP: "));

            // Verify Assassin-specific output.
            assertTrue(result.contains("Class: Assassin"));

        } finally {
            // Restore the original System.out.
            System.setOut(originalOut);
        }
    }
}
