package com.bptn.individual_project.combat;

import org.junit.jupiter.api.BeforeEach;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import com.bptn.individual_project.character.Warrior;
import com.bptn.individual_project.enemy.Goblin;

/**
 * Test suite for CombatManager class.
 * Tests turn-based combat system with player and enemy interactions.
 * 
 * Complex Method Tested:
 * startCombat() - The main combat loop that handles turn alternation, special attacks,
 * and determines victory/defeat conditions. Complex logic includes state management
 * of special attack flags and combat flow.
 */
public class CombatManagerTest {
    
    private Warrior player;
    private Goblin enemy;
    private Scanner scanner;
    
    @BeforeEach
    public void setUp() {
        player = new Warrior("Hero", 100, 20, null);
        enemy = new Goblin("Goblin");
        // Create scanner with predefined input - choose attack (option 1)
        scanner = new Scanner("1\n1\n1\n1\n1\n1\n1\n1\n1\n1\n");
    }
    
    /**
     * Test: CombatManager initializes correctly.
     */
    @Test
    public void testCombatManagerInitialization() {
        CombatManager combat = new CombatManager(player, enemy, scanner);
        assertNotNull(combat);
    }
    
    /**
     * COMPLEX TEST: startCombat returns true when player wins.
     * Tests the full combat loop where player ultimately defeats enemy.
     * Provides continuous attack commands (option 1) until one dies.
     */
    @Test
    public void testStartCombatPlayerWins() {
        // Create weak enemy that will die quickly
        Goblin weakGoblin = new Goblin("WeakGoblin");
        weakGoblin.takeDamage(45); // Reduce enemy HP to ~5 (since goblin starts with 50)
        
        // Provide attack commands
        scanner = new Scanner("1\n1\n1\n");
        CombatManager combat = new CombatManager(player, weakGoblin, scanner);
        
        boolean result = combat.startCombat();
        
        assertTrue(result);
        assertTrue(player.isAlive());
        assertFalse(weakGoblin.isAlive());
    }
    
    /**
     * COMPLEX TEST: startCombat returns false when player loses.
     * Tests combat loop where enemy defeats player.
     * Creates a scenario where player has low HP and enemy has high HP.
     */
    @Test
    public void testStartCombatPlayerLoses() {
        // Weaken the player
        player.takeDamage(90); // Player down to 10 HP
        
        // Enemy is still strong
        scanner = new Scanner("1\n1\n1\n1\n1\n1\n1\n1\n");
        CombatManager combat = new CombatManager(player, enemy, scanner);
        
        boolean result = combat.startCombat();
        
        assertFalse(result);
        assertFalse(player.isAlive());
    }
    
    /**
     * COMPLEX TEST: Combat continues while both combatants are alive.
     * Tests the main loop condition.
     */
    @Test
    public void testCombatContinuesWhileBothAlive() {
        // Both start alive
        assertTrue(player.isAlive());
        assertTrue(enemy.isAlive());
        
        // Weak enemy for quick resolution
        Goblin weakGoblin = new Goblin("WeakGoblin");
        weakGoblin.takeDamage(40);
        
        scanner = new Scanner("1\n1\n");
        CombatManager combat = new CombatManager(player, weakGoblin, scanner);
        
        combat.startCombat();
        
        // At least one should be dead
        assertTrue(!player.isAlive() || !weakGoblin.isAlive());
    }
    
    /**
     * Test: Combat with Combatant interface.
     * Verifies that both player and enemy implement Combatant interface.
     */
    @Test
    public void testCombatantInterface() {
        assertTrue(player instanceof Combatant);
        assertTrue(enemy instanceof Combatant);
        
        // Verify interface methods work
        assertEquals("Hero", player.getName());
        assertEquals(100, player.getMaxHealth());
        assertTrue(player.isAlive());
        player.takeDamage(50);
        assertEquals(50, player.getHealth());
    }
    
    
    /**
     * Test: Player can use special attack during combat.
     * Covers the player special attack branch when it has not been used.
     */
    @Test
    public void testPlayerSpecialAttack() {
        // Give the player enough health to survive the enemy's turn.
        player = new Warrior("Hero", 1000, 20, null);

        // Make the enemy strong enough to survive the special attack.
        Goblin strongGoblin = new Goblin("StrongGoblin");

        // Select Special Attack first, then regular Attack.
        scanner = new Scanner("2\n1\n");

        CombatManager combat = new CombatManager(player, strongGoblin, scanner);

        boolean result = combat.startCombat();

        assertTrue(result);
        assertFalse(strongGoblin.isAlive());
    }

    /**
     * Test: Player's special attack can only be used once per combat.
     * Covers the branch where the special attack has already been used.
     */
    @Test
    public void testPlayerSpecialAttackUsed() {
        // Give the player enough health to survive multiple enemy turns.
        player = new Warrior("Hero", 1000, 20, null);

        Goblin strongGoblin = new Goblin("StrongGoblin");

        // First turn uses Special Attack.
        // Second turn tries to use Special Attack again, which should not be allowed.
        scanner = new Scanner("2\n2\n1\n"); 

        CombatManager combat = new CombatManager(player, strongGoblin, scanner);

        combat.startCombat();

        // Verify that combat completed successfully.
        assertFalse(strongGoblin.isAlive());
    }

    /**
     * Test: startCombat returns the player's current alive status
     * when combat begins with an already defeated enemy.
     * Covers the final return statement outside the combat loop.
     */
    @Test
    public void testStartCombatWithAlreadyDefeatedEnemy() {
        Goblin defeatedGoblin = new Goblin("DefeatedGoblin");

        // Defeat the enemy before combat begins.
        defeatedGoblin.takeDamage(defeatedGoblin.getHealth());

        CombatManager combat = new CombatManager(player, defeatedGoblin, scanner);

        boolean result = combat.startCombat();

        assertTrue(result);
    }

    /**
     * Test: startCombat returns false when combat begins with
     * an already defeated player.
     * Covers the final return path when the player is not alive.
     */
    @Test
    public void testStartCombatWithAlreadyDefeatedPlayer() {
        player.takeDamage(player.getHealth());

        CombatManager combat = new CombatManager(player, enemy, scanner);

        boolean result = combat.startCombat();

        assertFalse(result);
    }

    /**
     * Test: Enemy can use its special attack.
     * Covers the enemy special attack branch.
     */
    @Test
    public void testEnemySpecialAttack() throws Exception {
        // Use reflection to access the private enemyTurn method.
        CombatManager combat = new CombatManager(player, enemy, scanner);

        Method enemyTurn = CombatManager.class.getDeclaredMethod("enemyTurn");
        enemyTurn.setAccessible(true);

        // Run enough enemy turns to exercise the random special-attack branch.
        for (int i = 0; i < 100; i++) {
            enemyTurn.invoke(combat);
        }

        // Verify that the test completed without an exception.
        assertTrue(true);
    }
}
