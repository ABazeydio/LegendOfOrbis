package com.bptn.individual_project.inventory;

import java.util.ArrayList;

import java.util.HashMap;

import com.bptn.individual_project.character.GameCharacter;
import com.bptn.individual_project.item.Equipment;
import com.bptn.individual_project.item.EquipmentSlot;
import com.bptn.individual_project.item.Item;

public class Inventory {

    private ArrayList<Item> items;              // everything carried
    private HashMap<EquipmentSlot, Item> equipped;  // one item per slot 

    public Inventory() {
        this.items = new ArrayList<>();
        this.equipped = new HashMap<>();
    }

    //adds item to inventory
    public void addItem(Item item) {
        items.add(item);
    }

    /*
     * Equips an item from the inventory.
     * If another piece of equipment is already equipped in the same slot,
     * the old equipment is automatically unequipped before the new one is equipped.
     */
    public boolean equipItem(GameCharacter character, int index) {

        // Validate that the selected inventory index exists.
        if (index < 0 || index >= items.size()) {
            return false;
        }

        // Retrieve the selected inventory item.
        Equipment newEquipment = (Equipment) items.get(index);

        // Check whether another item is already equipped in this slot.
        Equipment currentEquipment = (Equipment) equipped.get(newEquipment.getSlot());

        // If equipment already exists in this slot, remove its bonuses,
        // return it to the inventory, and notify the player.
        if (currentEquipment != null) {
            currentEquipment.removeBonus(character);
            items.add(currentEquipment);

            System.out.println(currentEquipment.getName()
                    + " was unequipped and returned to your bag.");
        }

        // Place the new equipment into the appropriate equipment slot.
        equipped.put(newEquipment.getSlot(), newEquipment);

        // Remove the equipped item from the inventory since it is now worn.
        items.remove(index);

        // Apply the equipment's stat bonuses to the character.
        newEquipment.use(character);

        // Equipment was successfully equipped.
        return true;
    }

    /*
     * Shows currently worn gear (the HashMap) and bag contents (the ArrayList).
     */
    public void displayInventory() {
        System.out.println("Equipped:");
        if (equipped.isEmpty()) {
            System.out.println("  (nothing)");
        } else {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                Item item = equipped.get(slot);
                if (item != null) {
                    System.out.println("  " + slot + ": " + item.getName());
                }
            }
        }

        System.out.println("Bag:");
        if (items.isEmpty()) {
            System.out.println("  (empty)");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + items.get(i).getName());
        }
    }

    /*
     * Number of unequipped items currently carried.
     * Used by GameEngine to bound the equip-menu choice.
     */
    public int getSize() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /*
     * Empties carried and equipped items — used when starting a new playthrough.
     */
    public void clear() {
        items.clear();
        equipped.clear();
    }
}
