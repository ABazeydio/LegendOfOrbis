package com.bptn.individual_project.character;

import java.util.List;
import java.util.Random;

/*
 * This class represents a Warrior character in the game. It extends the abstract GameCharacter class and implements its own unique attack and special attack behaviors.
 */

public class Warrior extends GameCharacter {
	
	

	public Warrior(String name, int health, int attackPower, Race race) {
		super(name, health, attackPower, race);
	}
	
	// Random instance for selecting attack messages
	private static final Random random = new Random();

	//list of attack messages for the Warrior class
	private static final List<String> ATTACK_MESSAGES = List.of(
		    "swings a sword at",
		    "slashes wildly at",
		    "throws a shield as distraction and strikes"
		);

	@Override
	public void attack(GameCharacter target) {
	    String verb = ATTACK_MESSAGES.get(random.nextInt(ATTACK_MESSAGES.size()));
	    System.out.println(getName() + " " + verb + " " + target.getName() + " for " + getAttackPower() + " damage!");
	    target.takeDamage(getAttackPower());
	}

	@Override
	public void specialAttack(GameCharacter target) {
		System.out.println(getName() + " performs a POWERFUL whirlwind attack on " + target.getName() + " for " + (getAttackPower() * 2) + " damage!");
		target.takeDamage(getAttackPower() * 2);
	}

	@Override
	public void displayStats() {
	    super.displayStats();
	    System.out.println("Class: Warrior");
	}
	
	

}
