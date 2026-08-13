package com.bptn.individual_project.character;

import java.util.List;
import java.util.Random;

/*
 * This class represents an Assassin character in the game. It extends the abstract GameCharacter class and implements its own unique attack and special attack behaviors.
 */

public class Assassin extends GameCharacter {

	public Assassin(String name, int health, int attackPower, Race race) {
		super(name, health, attackPower, race);
	}
	
	// Random instance for selecting attack messages
	private static final Random random = new Random();


	//list of attack messages for the Mage class
		private static final List<String> ATTACK_MESSAGES = List.of(
			    "performs a stealthy strike on",
			    "throws 2 daggers at",
			    "sneaks behind and stabs"
			);

		@Override
		public void attack(GameCharacter target) {
		    String verb = ATTACK_MESSAGES.get(random.nextInt(ATTACK_MESSAGES.size()));
		    System.out.println(getName() + " " + verb + " " + target.getName() + " for " + getAttackPower() + " damage!");
		    target.takeDamage(getAttackPower());
		}

		@Override
		public void specialAttack(GameCharacter target) {
			System.out.println(getName() + " summons shadow clones that teleport and stab " + target.getName() + " for " + (getAttackPower() * 2) + " damage!");
			target.takeDamage(getAttackPower() * 2);
		}
		
		@Override
		public void displayStats() {
		    super.displayStats();
		    System.out.println("Class: Assassin");
		}
}
