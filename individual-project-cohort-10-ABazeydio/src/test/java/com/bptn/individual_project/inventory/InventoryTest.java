package com.bptn.individual_project.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bptn.individual_project.character.Warrior;
import com.bptn.individual_project.item.Equipment;
import com.bptn.individual_project.item.EquipmentSlot;

/**
 * Comprehensive test suite for Inventory class.
 * Tests inventory management and equipment system.
 * 
 * Complex Method Tested:
 * equipItem() - Tests complex equipment swapping logic with slot management,
 * bonus application/removal, and inventory state management.
 */
public class InventoryTest {
    
    private Inventory inventory;
    private Warrior character;
    private Equipment weapon;
    private Equipment armor;
    
    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        character = new Warrior("TestHero", 100, 20, null);
        weapon = new Equipment("IronSword", EquipmentSlot.WEAPON, 10);
        armor = new Equipment("LeatherArmor", EquipmentSlot.ARMOR, 15);
    }
    
    
    /**
     * Test: Inventory initializes empty.
     */
    @Test
    public void testInventoryEmpty() {
        assertTrue(inventory.isEmpty());
        assertEquals(0, inventory.getSize());
    }
    
    /**
     * Test: Adding items increases inventory size.
     */
    @Test
    public void testAddItem() {
        inventory.addItem(weapon);
        assertEquals(1, inventory.getSize());
        assertFalse(inventory.isEmpty());
    }
    
    
    /**
     * Test: Clear empties inventory.
     */
    @Test
    public void testClearInventory() {
        inventory.addItem(weapon);
        inventory.addItem(armor);
        inventory.clear();
        
        assertTrue(inventory.isEmpty());
        assertEquals(0, inventory.getSize());
    }
    
    
    /**
     * COMPLEX TEST: equipItem with valid index equips the item.
     * Tests basic equipment functionality.
     */
    @Test
    public void testEquipItemValid() {
        inventory.addItem(weapon);
        
        int initialAttack = character.getAttackPower();
        boolean result = inventory.equipItem(character, 0);
        
        assertTrue(result);
        assertEquals(initialAttack + weapon.getStatBonus(), character.getAttackPower());
        assertEquals(0, inventory.getSize()); // Item removed from inventory
    }
    
    /**
     * COMPLEX TEST: equipItem with invalid index returns false.
     * Tests bounds checking.
     */
    @Test
    public void testEquipItemInvalidIndex() {
        inventory.addItem(weapon);
        
        boolean result = inventory.equipItem(character, 5); // Out of bounds
        
        assertFalse(result);
        assertEquals(1, inventory.getSize()); // Item still in inventory
    }
    
    /**
     * COMPLEX TEST: equipItem with negative index returns false.
     */
    @Test
    public void testEquipItemNegativeIndex() {
        inventory.addItem(weapon);
        
        boolean result = inventory.equipItem(character, -1);
        
        assertFalse(result);
        assertEquals(1, inventory.getSize());
    }
    
    /**
     * COMPLEX TEST: equipItem weapon applies attack bonus.
     */
    @Test
    public void testEquipItemWeaponBonus() {
        inventory.addItem(weapon); // +10 attack
        
        int beforeAttack = character.getAttackPower();
        inventory.equipItem(character, 0);
        int afterAttack = character.getAttackPower();
        
        assertEquals(10, afterAttack - beforeAttack);
    }
    
    /**
     * COMPLEX TEST: equipItem armor applies health bonus.
     */
    @Test
    public void testEquipItemArmorBonus() {
        inventory.addItem(armor); // +15 health
        
        int beforeHealth = character.getMaxHealth();
        inventory.equipItem(character, 0);
        int afterHealth = character.getMaxHealth();
        
        assertEquals(15, afterHealth - beforeHealth);
    }
    
    /**
     * COMPLEX TEST: Equipping second weapon in same slot removes first weapon.
     * Tests the slot replacement logic with same slot equipment.
     */
    @Test
    public void testEquipItemReplaceWeapon() {
        Equipment weapon1 = new Equipment("IronSword", EquipmentSlot.WEAPON, 10);
        Equipment weapon2 = new Equipment("SteelSword", EquipmentSlot.WEAPON, 15);
        
        inventory.addItem(weapon1);
        inventory.addItem(weapon2);
        
        // Equip first weapon (+10)
        inventory.equipItem(character, 0);
        // Equip second weapon (should replace first; +15)
        inventory.equipItem(character, 0); // weapon2 is now at index 0

        // Base attack 20 + second weapon 15 = 35
        assertEquals(35, character.getAttackPower());
    }
    
    /**
     * COMPLEX TEST: Equipping armor when already equipped replaces old armor.
     * Tests the complex unequip and re-equip logic.
     */
    @Test
    public void testEquipItemReplaceArmor() {
        Equipment armor1 = new Equipment("LeatherArmor", EquipmentSlot.ARMOR, 10);
        Equipment armor2 = new Equipment("ChainMail", EquipmentSlot.ARMOR, 20);
        
        inventory.addItem(armor1);
        inventory.addItem(armor2);
        
        // Equip first armor
        inventory.equipItem(character, 0);

        // Equip armor2
        inventory.equipItem(character, 0);
        
        int secondItemEquipped = character.getMaxHealth();
        
        // Should have only the bonus from armor2 (20) applied to base health (100)
        assertEquals(120, secondItemEquipped);
    }
    
    /**
     * COMPLEX TEST: Unequipped item returned to inventory when replaced.
     * Tests that old equipment goes back to inventory when swapped.
     */
    @Test
    public void testEquipItemUnequippedReturned() {
        Equipment weapon1 = new Equipment("IronSword", EquipmentSlot.WEAPON, 10);
        Equipment weapon2 = new Equipment("SteelSword", EquipmentSlot.WEAPON, 15);
        
        inventory.addItem(weapon1);
        inventory.addItem(weapon2);
        
        // Equip first weapon
        inventory.equipItem(character, 0);
        assertEquals(1, inventory.getSize()); // weapon2 remains
        
        // Equip second weapon (first should be returned)
        inventory.equipItem(character, 0);
        assertEquals(1, inventory.getSize()); // weapon1 returned, should be back in inventory
    }
    
    /**
     * COMPLEX TEST: Multiple equipment slots can be filled independently.
     * Tests that weapon and armor can both be equipped simultaneously.
     */
    @Test
    public void testEquipItemMultipleSlots() {
        inventory.addItem(weapon); // +10 attack
        inventory.addItem(armor);  // +15 health
        
        inventory.equipItem(character, 0); // Equip weapon
        int attackAfterWeapon = character.getAttackPower();
        int healthAfterWeapon = character.getMaxHealth();
        
        inventory.equipItem(character, 0); // Equip armor (it's now at index 0)
        int attackAfterArmor = character.getAttackPower();
        int healthAfterArmor = character.getMaxHealth();
        
        // Attack should stay same, health should increase
        assertEquals(attackAfterWeapon, attackAfterArmor);
        assertEquals(15, healthAfterArmor - healthAfterWeapon);
    }
    

 /**
  * Test: displayInventory shows "(nothing)" when no equipment is equipped.
  * Covers the empty equipped HashMap branch.
  */
 @Test
 public void testDisplayInventoryEmptyEquipped() {
     // Capture console output.
     ByteArrayOutputStream output =
             new ByteArrayOutputStream();
     PrintStream originalOut = System.out;

     System.setOut(new PrintStream(output));

     try {
         inventory.displayInventory();

         String consoleOutput = output.toString();

         assertTrue(consoleOutput.contains("Equipped:"));
         assertTrue(consoleOutput.contains("  (nothing)"));
     } finally {
         // Restore the original console output.
         System.setOut(originalOut);
     }
 }

 /**
  * Test: displayInventory shows "(empty)" when the bag is empty.
  * Covers the empty items ArrayList branch and early return.
  */
 @Test
 public void testDisplayInventoryEmptyBag() {
     // Capture console output.
	 ByteArrayOutputStream output =
             new ByteArrayOutputStream();
	 PrintStream originalOut = System.out;
    
     System.setOut(new PrintStream(output));

     try {
         inventory.displayInventory();

         String consoleOutput = output.toString();

         assertTrue(consoleOutput.contains("Bag:"));
         assertTrue(consoleOutput.contains("  (empty)"));
     } finally {
         // Restore the original console output.
         System.setOut(originalOut);
     }
 }

 /**
  * Test: displayInventory shows items currently in the bag.
  * Covers the non-empty items branch and the bag for-loop.
  */
 @Test
 public void testDisplayInventoryWithBagItems() {
     inventory.addItem(weapon);
     inventory.addItem(armor);

     // Capture console output.
     ByteArrayOutputStream output =
             new ByteArrayOutputStream();
     PrintStream originalOut = System.out;

     System.setOut(new PrintStream(output));

     try {
         inventory.displayInventory();

         String consoleOutput = output.toString();

         assertTrue(consoleOutput.contains("Bag:"));
         assertTrue(consoleOutput.contains("1. IronSword"));
         assertTrue(consoleOutput.contains("2. LeatherArmor"));
     } finally {
         // Restore the original console output.
         System.setOut(originalOut);
     }
 }

 /**
  * Test: displayInventory shows currently equipped equipment.
  * Covers the non-empty equipped branch and the item != null condition.
  */
 @Test
 public void testDisplayInventoryWithEquippedItem() {
     inventory.addItem(weapon);

     inventory.equipItem(character, 0);

     // Capture console output.
     ByteArrayOutputStream output =
             new ByteArrayOutputStream();
     PrintStream originalOut = System.out;

     System.setOut(new PrintStream(output));

     try {
         inventory.displayInventory();

         String consoleOutput = output.toString();

         assertTrue(consoleOutput.contains("Equipped:"));
         assertTrue(consoleOutput.contains("WEAPON: IronSword"));
         assertTrue(consoleOutput.contains("Bag:"));
         assertTrue(consoleOutput.contains("  (empty)"));
     } finally {
         // Restore the original console output.
         System.setOut(originalOut);
     }
 }

}
