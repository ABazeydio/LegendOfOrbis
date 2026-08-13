package com.bptn.individual_project.enemy;

import java.util.List;
import java.util.Random;

import com.bptn.individual_project.character.GameCharacter;


/*
 * This class represents a Troll enemy in the game. It extends the Enemy class and provides specific implementations for the attack and special attack methods.
 */
public class Troll extends Enemy {

    private static final List<String> ATTACK_LINES = List.of(
        "smashes a club into",
        "slams a heavy fist at",
        "tramples toward"
    );

    private static final Random random = new Random();

    public Troll(String name) {
        super(name, 100, 14, null, 25, 35);
    }

    @Override
    public void attack(GameCharacter target) {
        String line = ATTACK_LINES.get(random.nextInt(ATTACK_LINES.size()));
        com.bptn.individual_project.util.MessageLogger.println(getName() + " " + line + " " + target.getName() + " for " + getAttackPower() + " damage!");
        target.takeDamage(getAttackPower());
    }

    @Override
    public void specialAttack(GameCharacter target) {
        int healAmount = 50;
        heal(healAmount);
        com.bptn.individual_project.util.MessageLogger.println(getName() + " uses Regenerate and recovers " + healAmount + " health!");
    }

    @Override
    public void displayStats() {
        com.bptn.individual_project.util.MessageLogger.println("Name: " + getName());
        com.bptn.individual_project.util.MessageLogger.println("Health: " + getHealth() + "/" + getMaxHealth());
        com.bptn.individual_project.util.MessageLogger.println("Attack Power: " + getAttackPower());
    }

}
