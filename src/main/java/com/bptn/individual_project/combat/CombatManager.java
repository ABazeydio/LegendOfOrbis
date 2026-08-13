package com.bptn.individual_project.combat;

import com.bptn.individual_project.character.GameCharacter;
import com.bptn.individual_project.enemy.Enemy;
import com.bptn.individual_project.util.InputValidator;

import java.util.Random;
import java.util.Scanner;

/*
 * Manages turn-based combat between a player character and an enemy.
 * Delegates all narration and damage logic to each combatant's own
 */
public class CombatManager {

    private final GameCharacter player;
    private final Enemy enemy;
    private final Scanner scanner;
    private final Random random;

    private boolean playerSpecialUsed = false;
    private boolean enemySpecialUsed = false;

    public CombatManager(GameCharacter player, Enemy enemy, Scanner scanner) {
        this.player = player;
        this.enemy = enemy;
        this.scanner = scanner;
        this.random = new Random();
    }

    /*
     * Runs the full combat loop until one combatant is defeated.
     * Returns true if the player won, false if the player was defeated.
     */
    public boolean startCombat() {
        com.bptn.individual_project.util.MessageLogger.println("\n========================================");
        com.bptn.individual_project.util.MessageLogger.println("  COMBAT START: " + player.getName() + " vs " + enemy.getName());
        com.bptn.individual_project.util.MessageLogger.println("========================================");

        while (player.isAlive() && enemy.isAlive()) {
            printStatus();

            // --- Player's Turn ---
            playerTurn();

            if (!enemy.isAlive()) {
                com.bptn.individual_project.util.MessageLogger.println("\n" + enemy.getName() + " has been defeated!");
                return true;
            }

            // --- Enemy's Turn ---
            com.bptn.individual_project.util.MessageLogger.println("\n--- " + enemy.getName() + "'s turn ---");
            enemyTurn();

            if (!player.isAlive()) {
                com.bptn.individual_project.util.MessageLogger.println("\n" + player.getName() + " has been defeated...");
                return false;
            }
        }

        return player.isAlive();
    }

    /*
     * Handles the player's turn by reading their action choice.
     * Special attack is only available once per fight.
     */
    private void playerTurn() {
        com.bptn.individual_project.util.MessageLogger.println("\n--- Your Turn ---");

        com.bptn.individual_project.util.MessageLogger.println("1. Attack");

        if (!playerSpecialUsed) {
            com.bptn.individual_project.util.MessageLogger.println("2. Special Attack (once per fight)");

            int choice = InputValidator.getValidatedMenuChoice(scanner, 1, 2);

            if (choice == 2) {
                playerSpecialUsed = true;
                player.specialAttack(enemy);
                return;
            }
        } else {
            com.bptn.individual_project.util.MessageLogger.println("(Special attack already used this fight)");
            InputValidator.getValidatedMenuChoice(scanner, 1, 1);
        }

        player.attack(enemy);
    }

    /*
     * Handles the enemy's turn. The enemy randomly chooses between
     * attack() and specialAttack(). Special can only fire once.
     */
    private void enemyTurn() {
        boolean useSpecial = !enemySpecialUsed && random.nextBoolean();

        if (useSpecial) {
            enemySpecialUsed = true;
            enemy.specialAttack(player);
        } else {
            enemy.attack(player);
        }
    }

    /*
     * Prints each combatant's current HP before the player's turn.
     */
    private void printStatus() {
        com.bptn.individual_project.util.MessageLogger.println("\n----------------------------------------");
        com.bptn.individual_project.util.MessageLogger.printf("  %-20s HP: %d/%d%n", player.getName(), player.getHealth(), player.getMaxHealth());
        com.bptn.individual_project.util.MessageLogger.printf("  %-20s HP: %d/%d%n", enemy.getName(), enemy.getHealth(), enemy.getMaxHealth());
        com.bptn.individual_project.util.MessageLogger.println("----------------------------------------");
    }

}
