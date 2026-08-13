package com.bptn.individual_project.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.bptn.individual_project.character.Warrior;

/**
 * Test suite for Item abstract class.
 * Tests basic item functionality using Equipment as concrete implementation.
 */
public class ItemTest {
    
    private Equipment testItem;
    
    @BeforeEach
    public void setUp() {
        testItem = new Equipment("TestItem", EquipmentSlot.WEAPON, 10);
    }
    
    /**
     * Test: Item has a name property.
     */
    @Test
    public void testItemName() {
        assertEquals("TestItem", testItem.getName());
    }
    
    /**
     * Test: Item has a value property.
     */
    @Test
    public void testItemValue() {
        assertEquals(10, testItem.getValue());
    }
}
