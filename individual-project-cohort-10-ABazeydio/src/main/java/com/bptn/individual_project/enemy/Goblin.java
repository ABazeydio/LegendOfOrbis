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
        System.out.println(getName() + " " + line + " " + target.getName() + " for " + getAttackPower() + " damage!");
        target.takeDamage(getAttackPower());
    }

    @Override
    public void specialAttack(GameCharacter target) {
        int damage = getAttackPower() + 5;
        System.out.println(getName() + " scurries around and gets a Sneaky Strike on " + target.getName() + " for " + damage + " damage!");
        target.takeDamage(damage);
    }

    @Override
    public void displayStats() {
        System.out.println("Name: " + getName());
        System.out.println("Health: " + getHealth() + "/" + getMaxHealth());
        System.out.println("Attack Power: " + getAttackPower());
    }

}