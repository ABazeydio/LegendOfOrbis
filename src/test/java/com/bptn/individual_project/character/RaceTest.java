package com.bptn.individual_project.character;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for Race enum.
 * Tests race buffs and string representation.
 */
public class RaceTest {
    
    /**
     * Test: HUMAN race has no buffs.
     */
    @Test
    public void testHumanRaceBuffs() {
        Race human = Race.HUMAN;
        assertEquals(0, human.getHealthBuff());
        assertEquals(0, human.getAttackBuff());
    }
    
    /**
     * Test: DEMONIC race has +20/+20 buffs.
     */
    @Test
    public void testDemonicRaceBuffs() {
        Race demonic = Race.DEMONIC;
        assertEquals(20, demonic.getHealthBuff());
        assertEquals(20, demonic.getAttackBuff());
    }
    
    /**
     * Test: ANGELIC race has +30/+30 buffs.
     */
    @Test
    public void testAngelicRaceBuffs() {
        Race angelic = Race.ANGELIC;
        assertEquals(30, angelic.getHealthBuff());
        assertEquals(30, angelic.getAttackBuff());
    }
    
    /**
     * Test: All race values are accessible.
     */
    @Test
    public void testRaceValues() {
        Race[] races = Race.values();
        assertEquals(3, races.length);
        assertTrue(contains(races, Race.HUMAN));
        assertTrue(contains(races, Race.DEMONIC));
        assertTrue(contains(races, Race.ANGELIC));
    }
    
    /**
     * Test: Race toString format includes name and buffs.
     */
    @Test
    public void testRaceToString() {
        String humanStr = Race.HUMAN.toString();
        assertTrue(humanStr.contains("HUMAN"));
        assertTrue(humanStr.contains("0"));
        
        String demonicStr = Race.DEMONIC.toString();
        assertTrue(demonicStr.contains("DEMONIC"));
        assertTrue(demonicStr.contains("20"));
        
        String angelicStr = Race.ANGELIC.toString();
        assertTrue(angelicStr.contains("ANGELIC"));
        assertTrue(angelicStr.contains("30"));
    }
    
    /**
     * Helper method to check if array contains a value.
     */
    private boolean contains(Race[] array, Race target) {
        for (Race race : array) {
            if (race == target) {
                return true;
            }
        }
        return false;
    }
}
