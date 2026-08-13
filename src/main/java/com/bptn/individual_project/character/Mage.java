package com.bptn.individual_project.character;

import java.util.List;
import java.util.Random;

/*
 * This class represents a Mage character in the game. It extends the abstract GameCharacter class and implements its own unique attack and special attack behaviors.
 */
public class Mage extends GameCharacter {

	public Mage(String name, int health, int attackPower, Race race) {
		super(name, health, attackPower, race);
	}
	
	// Random instance for selecting attack messages
	private static final Random random = new Random();

	
	//list of attack messages for the Mage class
	private static final List<String> ATTACK_MESSAGES = List.of(
		    "launches a fireball at",
		    "casts a lightning bolt at",
		    "performs powerful arcane explosion on"
		);

	@Override
	public void attack(GameCharacter target) {
	    String verb = ATTACK_MESSAGES.get(random.nextInt(ATTACK_MESSAGES.size()));
	    com.bptn.individual_project.util.MessageLogger.println(getName() + " " + verb + " " + target.getName() + " for " + getAttackPower() + " damage!");
	    target.takeDamage(getAttackPower());
	}

	@Override
	public void specialAttack(GameCharacter target) {
		com.bptn.individual_project.util.MessageLogger.println(getName() + " summons a volcano engulfed with flames at " + target.getName() + " for " + (getAttackPower() * 2) + " damage!");
		target.takeDamage(getAttackPower() * 2);
	}
	
	@Override
	public void displayStats() {
	    super.displayStats();
	    com.bptn.individual_project.util.MessageLogger.println("Class: Mage");
	}
	
}
