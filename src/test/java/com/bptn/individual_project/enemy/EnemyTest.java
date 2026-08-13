package com.bptn.individual_project.enemy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.bptn.individual_project.character.Warrior;

/**
 * Test suite for Enemy abstract class.
 * Tests reward system and inheritance properties.
 */
public class EnemyTest {
    
    private Goblin goblin;
    private Warrior target;
    
    @BeforeEach
    public void setUp() {
        goblin = new Goblin("TestGoblin");
        target = new Warrior("Hero", 100, 20, null);
    }
    
    /**
     * Test: Enemy has correct gold reward.
     */
    @Test
    public void testEnemyGoldReward() {
        assertEquals(10, goblin.getGoldReward());
    }
    
    /**
     * Test: Enemy has correct XP reward.
     */
    @Test
    public void testEnemyXpReward() {
        assertEquals(15, goblin.getXpReward());
    }
    
    /**
     * Test: Enemy initializes with correct stats.
     */
    @Test
    public void testEnemyInitialization() {
        assertEquals("TestGoblin", goblin.getName());
        assertEquals(50, goblin.getHealth());
        assertEquals(50, goblin.getMaxHealth());
        assertEquals(20, goblin.getAttackPower());
    }
}
