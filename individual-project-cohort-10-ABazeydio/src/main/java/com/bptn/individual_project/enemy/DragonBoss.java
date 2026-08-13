package com.bptn.individual_project.enemy;

import java.util.List;
import java.util.Random;

import com.bptn.individual_project.character.GameCharacter;
import com.bptn.individual_project.character.Race;

/*
 * This class represents a Dragon Boss enemy in the game. It extends the Enemy class and provides specific implementations for the attack and special attack methods.
 */

public class DragonBoss extends Enemy {

    private static final List<String> ATTACK_LINES = List.of(
        "breathes searing flame at",
        "rakes massive claws across",
        "whips its tail into"
    );

    private static final Random random = new Random();

    public DragonBoss(String name) {
        super(name, 150, 25, Race.DEMONIC, 100, 200);
    }

    @Override
    public void attack(GameCharacter target) {
        String line = ATTACK_LINES.get(random.nextInt(ATTACK_LINES.size()));
        System.out.println(getName() + " " + line + " " + target.getName() + " for " + getAttackPower() + " damage!");
        target.takeDamage(getAttackPower());
    }

    @Override
    public void specialAttack(GameCharacter target) {
        int damage = getAttackPower() + 15;
        System.out.println(getName() + " unleashes an Armor-Piercing Strike on " + target.getName() + " for " + damage + " damage, ignoring defenses!");
        target.takeDamage(damage);
    }

    @Override
    public void displayStats() {
        System.out.println("Name: " + getName());
        System.out.println("Health: " + getHealth() + "/" + getMaxHealth());
        System.out.println("Attack Power: " + getAttackPower());
        System.out.println("Race: Demonic");
    }

}