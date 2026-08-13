package com.bptn.individual_project.character;

/*
 * This enum contains all the valid race choices, when a user creates a character, race is randomly chosen.
 * Each race has its own unique buffs.
 */
public enum Race {
	
	HUMAN(0, 0),
	DEMONIC(20, 20),
	ANGELIC(30, 30);
	
	
	private final int healthBuff;
	private final int attackBuff;

	
	Race(int healthBuff, int attackBuff) {
		this.healthBuff = healthBuff;
		this.attackBuff = attackBuff;
	}


	public int getHealthBuff() {
		return healthBuff;
	}

	public int getAttackBuff() {
		return attackBuff;
		
	}
	
	@Override
	public String toString() {
		return "You are a " + this.name() + " (Health Buff: " + healthBuff + ", Attack Buff: " + attackBuff + ")";
	}
}
