package com.bptn.individual_project.item;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for EquipmentSlot enum.
 * Tests equipment slot types and values.
 */
public class EquipmentSlotTest {
    
	@Test
	public void testEquipmentSlots() {
	    assertEquals(EquipmentSlot.WEAPON, EquipmentSlot.valueOf("WEAPON"));
	    assertEquals(EquipmentSlot.ARMOR, EquipmentSlot.valueOf("ARMOR"));
	}
    
    /**
     * Test: Slots are different from each other.
     */
    @Test
    public void testSlotsAreDifferent() {
        assertNotEquals(EquipmentSlot.WEAPON, EquipmentSlot.ARMOR);
    }
}
