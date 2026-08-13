package com.bptn.individual_project.item;

import com.bptn.individual_project.character.GameCharacter;

/*
 * This is an abstract class that represents an item in the game. 
 * It serves as a base class for specific item types, such as products or services.
 * The Item class can contain common properties and methods that are shared among all item types, while allowing subclasses to implement their own specific behavior.
 */
public abstract class Item {
	
	private String name;
	private int value;

	public Item(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public abstract void use(GameCharacter target);
}
