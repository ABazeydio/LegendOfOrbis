package com.bptn.individual_project.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.bptn.individual_project.character.Warrior;

/**
 * Test suite for Equipment class.
 * Tests equipment properties and bonus application/removal.
 */
public class EquipmentTest {
    
    private Equipment weapon;
    private Equipment armor;
    private Warrior character;
    
    @BeforeEach
    public void setUp() {
        weapon = new Equipment("IronSword", EquipmentSlot.WEAPON, 10);
        armor = new Equipment("LeatherArmor", EquipmentSlot.ARMOR, 15);
        character = new Warrior("TestHero", 100, 20, null);
    }
    
    /**
     * Test: Equipment initializes with correct properties.
     */
    @Test
    public void testEquipmentInitialization() {
        assertEquals("IronSword", weapon.getName());
        assertEquals(EquipmentSlot.WEAPON, weapon.getSlot());
        assertEquals(10, weapon.getStatBonus());
    }
    
    /**
     * Test: Weapon equipment applies attack bonus via use().
     */
    @Test
    public void testWeaponUse() {
        int beforeAttack = character.getAttackPower();
        weapon.use(character);
        int afterAttack = character.getAttackPower();
        
        assertEquals(10, afterAttack - beforeAttack);
    }
    
    /**
     * Test: Armor equipment applies health bonus via use().
     */
    @Test
    public void testArmorUse() {
        int beforeHealth = character.getMaxHealth();
        armor.use(character);
        int afterHealth = character.getMaxHealth();
        
        assertEquals(15, afterHealth - beforeHealth);
    }
    
    /**
     * Test: removeBonus reverses weapon bonus.
     */
    @Test
    public void testRemoveWeaponBonus() {
        weapon.use(character);
        int afterUse = character.getAttackPower();
        
        weapon.removeBonus(character);
        int afterRemove = character.getAttackPower();
        
        assertEquals(10, afterUse - afterRemove);
    }
    
    /**
     * Test: removeBonus reverses armor bonus.
     */
    @Test
    public void testRemoveArmorBonus() {
        armor.use(character);
        int afterUse = character.getMaxHealth();
        
        armor.removeBonus(character);
        int afterRemove = character.getMaxHealth();
        
        assertEquals(15, afterUse - afterRemove);
    }
    
}
