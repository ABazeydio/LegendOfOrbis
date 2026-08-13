package com.bptn.individual_project.item;

import com.bptn.individual_project.character.GameCharacter;

/*
 * This is a class that represents an equipment item in the game.
 */
public class Equipment extends Item {

	private EquipmentSlot slot;
	private int statBonus; 
	

	public Equipment(String name, EquipmentSlot slot, int statBonus) {
		super(name, statBonus);
		this.slot = slot;
		this.statBonus = statBonus;
	}


	public EquipmentSlot getSlot() {
		return slot;
	}

	public int getStatBonus() {
		return statBonus;
	}

	@Override
	public void use(GameCharacter target) {
	    if (slot == EquipmentSlot.WEAPON) {
	        target.applyEquipmentBonus(statBonus, 0);
	    } else if (slot == EquipmentSlot.ARMOR) {
	        target.applyEquipmentBonus(0, statBonus);
	    }
	    com.bptn.individual_project.util.MessageLogger.println(target.getName() + " equips " + getName() + "!");
	}

	/*
	 * Reverses this item's stat bonus when it is unequipped / replaced.
	 * Prevents stacking if the player equips another item in the same slot.
	 */
	public void removeBonus(GameCharacter target) {
	    if (slot == EquipmentSlot.WEAPON) {
	        target.applyEquipmentBonus(-statBonus, 0);
	    } else if (slot == EquipmentSlot.ARMOR) {
	        target.applyEquipmentBonus(0, -statBonus);
	    }
	}

}
