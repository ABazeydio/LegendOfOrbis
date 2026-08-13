package com.bptn.individual_project.enemy;

import java.util.List;
import java.util.Random;

import com.bptn.individual_project.character.GameCharacter;

/*
 * This class represents a Goblin enemy in the game. It extends the Enemy class and provides specific implementations for the attack and special attack methods.
 */
public class Goblin extends Enemy {

    private static final List<String> ATTACK_LINES = List.of(
        "jabs a rusty dagger at",
        "lunges wildly at",
        "nips at"
    );

    private static final Random random = new Random();

    public Goblin(String name) {
        super(name, 50, 20, null, 10, 15);
    }

    @Override
    public void attack(GameCharacter target) {
        String line = ATTACK_LINES.get(random.nextInt(ATTACK_LINES.size()));
        com.bptn.individual_project.util.MessageLogger.println(getName() + " " + line + " " + target.getName() + " for " + getAttackPower() + " damage!");
        target.takeDamage(getAttackPower());
    }

    @Override
    public void specialAttack(GameCharacter target) {
        int damage = getAttackPower() + 5;
        com.bptn.individual_project.util.MessageLogger.println(getName() + " scurries around and gets a Sneaky Strike on " + target.getName() + " for " + damage + " damage!");
        target.takeDamage(damage);
    }

    @Override
    public void displayStats() {
        com.bptn.individual_project.util.MessageLogger.println("Name: " + getName());
        com.bptn.individual_project.util.MessageLogger.println("Health: " + getHealth() + "/" + getMaxHealth());
        com.bptn.individual_project.util.MessageLogger.println("Attack Power: " + getAttackPower());
    }

}
