package com.bptn.individual_project.character;

import com.bptn.individual_project.combat.Combatant;

/*
 * This abstract class that represents a character in the game. 
 * It contains common properties and methods that all characters share, such as name, health, and attack power.
 *  Specific character types(Classes) will extend this class and implement their own unique behaviors.
 */
public abstract class GameCharacter implements Combatant {
	
	
	private String name;
	private int level = 1;
	private int health;
	private int maxHealth;
	private int attackPower;
	private int experience;
	private int maxExperience;
	private int gold;
	private static final int XP_TO_LEVEL_UP = 100;
	private Race race;
	

	public GameCharacter(String name, int health, int attackPower, Race race) {
	    this.name = name;
	    this.race = race;
	    int healthBuff = (race != null) ? race.getHealthBuff() : 0;
	    int attackBuff = (race != null) ? race.getAttackBuff() : 0;
	    // Start at full (buffed) health so race bonuses apply immediately
	    this.maxHealth = health + healthBuff;
	    this.health = this.maxHealth;
	    this.attackPower = attackPower + attackBuff;
	    this.experience = 0;
	    this.maxExperience = XP_TO_LEVEL_UP;
	    this.gold = 0;
	}
	
	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public int getHealth() {
		return health;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public int getExperience() {
		return experience;
	}

	public int getGold() {
		return gold;
	}

	/*
	 * Adds gold after a combat victory (or other rewards).
	 * Negative amounts are ignored so gold never goes below zero unintentionally.
	 */
	public void addGold(int amount) {
		if (amount > 0) {
			this.gold += amount;
		}
	}

	/*
	 * Spends gold at the shop. Returns true if the purchase went through,
	 * false if the player cannot afford the cost.
	 */
	public boolean spendGold(int amount) {
		if (amount <= 0 || this.gold < amount) {
			return false;
		}
		this.gold -= amount;
		return true;
	}
	
	public void levelUp() {
		this.level++;
		this.maxHealth += 10; // Increase max health on level up
		this.health = this.maxHealth; // Restore health to max on level up
		this.attackPower += 10; // Increase attack power on level up 
		com.bptn.individual_project.util.MessageLogger.println("You leveled up! + 1");	
	}
	
	//An XP method that (eventually) triggers level-up logic
	public void earnExperience(int exp) {
		this.experience += exp;
		while (this.experience >= this.maxExperience) {
			this.experience -= this.maxExperience; // Reset experience after leveling up
			levelUp();
		}
	}
	
	//healing method
	public int heal(int healAmount) {
		this.health += healAmount;
		if (this.health > this.maxHealth) {
			this.health = this.maxHealth;
		}
		return this.health;
	}
	
	@Override
	public boolean isAlive() {
		return this.health > 0;
	}
	
	@Override
	public void takeDamage(int damage) {
		this.health -= damage;
		if (this.health <= 0) {
			this.health = 0;
		}
	} 
	
	/*
	 * Applies (or removes, if negative) equipment bonuses.
	 * Current HP is clamped so it never exceeds maxHealth after a change.
	 */
	public void applyEquipmentBonus(int bonusAttack, int bonusHealth) {

	    // Remember whether the character was at full health beforehand.
	    boolean wasAtFullHealth = (this.health == this.maxHealth);

	    this.attackPower += bonusAttack;
	    this.maxHealth += bonusHealth;

	    // If max health increased and the player was already at full health,
	    // increase current health by the same amount.
	    if (bonusHealth > 0 && wasAtFullHealth) {
	        this.health += bonusHealth;
	    }

	    // Ensure current health never exceeds the new maximum.
	    if (this.health > this.maxHealth) {
	        this.health = this.maxHealth;
	    }
	}

	public abstract void attack(GameCharacter target);
	
	public abstract void specialAttack(GameCharacter target);
	
	
	public void displayStats() {
	    com.bptn.individual_project.util.MessageLogger.println("Name: " + name + " | Level: " + level);
	    com.bptn.individual_project.util.MessageLogger.println("Race: " + race );
	    com.bptn.individual_project.util.MessageLogger.println("Health: " + health + "/" + maxHealth);
	    com.bptn.individual_project.util.MessageLogger.println("Attack Power: " + attackPower);
	    com.bptn.individual_project.util.MessageLogger.println("Gold: " + gold + " | XP: " + experience + "/" + maxExperience);
	}

}
