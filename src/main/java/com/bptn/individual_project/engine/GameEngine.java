package com.bptn.individual_project.engine;

import com.bptn.individual_project.character.Assassin;
import com.bptn.individual_project.character.GameCharacter;
import com.bptn.individual_project.character.Mage;
import com.bptn.individual_project.character.Race;
import com.bptn.individual_project.character.Warrior;
import com.bptn.individual_project.combat.CombatManager;
import com.bptn.individual_project.enemy.DragonBoss;
import com.bptn.individual_project.enemy.Enemy;
import com.bptn.individual_project.enemy.Goblin;
import com.bptn.individual_project.enemy.Troll;
import com.bptn.individual_project.inventory.Inventory;
import com.bptn.individual_project.item.Equipment;
import com.bptn.individual_project.item.EquipmentSlot;
import com.bptn.individual_project.util.InputValidator;

import java.util.Random;
import java.util.Scanner;

/*
 * GameEngine coordinates the high-level flow (character creation, main menu,
 * exploration, combat hand-off, inventory) but does not own business logic:
 * combat resolution lives in CombatManager, stats/XP/gold on GameCharacter,
 * item effects on Equipment, and all input validation in InputValidator.
 */
public class GameEngine {

    private final Scanner scanner;
    private final Random random;
    private final Inventory inventory;

    private GameCharacter player;
    private int stepsExplored;   // drives encounter difficulty weighting
    private boolean quitRequested;

    // Encounter chance when exploring a direction
    private static final double ENCOUNTER_CHANCE = 0.55;
    // Chance of an item drop after a win
    private static final double LOOT_DROP_CHANCE = 0.15;

    // Shop prices
    private static final int PRICE_ORBIS_BLADE = 150;
    private static final int PRICE_AEGIS_PLATE = 175;

    public GameEngine(Scanner scanner) {
        this.scanner = scanner;
        this.random = new Random();
        this.inventory = new Inventory();
        this.stepsExplored = 0;
        this.quitRequested = false;
    }

    /*
     * Entry point for a full play session. Supports restart after game-over
     * or quitting back to a fresh character.
     */
    public void start() {
        System.out.println("========================================");
        System.out.println("       LEGENDS OF ORBIS");
        System.out.println("========================================");

        boolean playAgain = true;
        while (playAgain) {
            createCharacter();
            mainLoop();

            if (!player.isAlive()) {
                System.out.println("\n*** GAME OVER ***");
                System.out.println(player.getName() + " has fallen in battle...");
            }

            playAgain = InputValidator.getYesNo(scanner, "Play again?");
            if (playAgain) {
                // Reset session state for a new run
                stepsExplored = 0;
                quitRequested = false;
                inventory.clear();
            }
        }

        System.out.println("\nThanks for playing Legends of Orbis!");
    }


    /*
     * Prompts for a validated name, randomly assigns a Race, then lets the
     * player pick Warrior / Mage / Assassin via a validated menu choice.
     */
    private void createCharacter() {
        System.out.println("\n--- Character Creation ---");

        String name = InputValidator.getValidatedName(scanner);

        // Race is assigned randomly at creation (per Race enum design)
        Race[] races = Race.values();
        Race race = races[random.nextInt(races.length)];
        System.out.println("The fates assign your lineage: " + race.name());
        System.out.println("  " + race);

        System.out.println("\nChoose your class:");
        System.out.println("1. Warrior  (sturdy — high HP, solid attack)");
        System.out.println("2. Mage     (glass cannon — lower HP, higher attack)");
        System.out.println("3. Assassin (balanced — mid HP and attack)");
        int classChoice = InputValidator.getValidatedMenuChoice(scanner, 1, 3);

        // Base stats tuned per class before race buffs are applied
        switch (classChoice) {
            case 1 -> player = new Warrior(name, 120, 12, race);
            case 2 -> player = new Mage(name, 80, 18, race);
            case 3 -> player = new Assassin(name, 100, 15, race);
        }

        System.out.println("\n" + player.getName() + " steps into Orbis!");
        player.displayStats();
    }


    /*
     * Presents the hub menu until the player quits or dies.
     * Dispatches to exploration, inventory, or stats — no combat math here.
     */
    private void mainLoop() {
        while (player.isAlive() && !quitRequested) {
            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. Explore");
            System.out.println("2. Inventory");
            System.out.println("3. Shop");
            System.out.println("4. View Stats");
            System.out.println("5. Quit");
            int choice = InputValidator.getValidatedMenuChoice(scanner, 1, 5);

            switch (choice) {
                case 1 -> explore();
                case 2 -> inventoryMenu();
                case 3 -> shopMenu();
                case 4 -> {
                    System.out.println();
                    player.displayStats();
                }
                case 5 -> {
                    if (InputValidator.getYesNo(scanner, "Are you sure you want to quit?")) {
                        quitRequested = true;
                    }
                }
            }
        }
    }


    /*
     * Asks for a validated direction, narrates the move, then rolls for
     * a random encounter. Encounter type is weighted by how far the
     * player has already explored (early = Goblin, later = Troll/Dragon).
     */
    private void explore() {
        char direction = InputValidator.getValidatedDirection(scanner);
        String directionName = switch (direction) {
            case 'N' -> "north";
            case 'E' -> "east";
            case 'S' -> "south";
            case 'W' -> "west";
            default -> "somewhere";
        };

        stepsExplored++;
        System.out.println("\nYou travel " + directionName + "...");

        if (random.nextDouble() < ENCOUNTER_CHANCE) {
            Enemy enemy = spawnEnemy();
            System.out.println("A wild " + enemy.getName() + " appears!");

            boolean won = new CombatManager(player, enemy, scanner).startCombat();
            resolveCombat(won, enemy);
        } else {
            System.out.println("The path is quiet. Nothing attacks you this time.");
        }
    }

    /*
     * Picks an enemy subtype weighted by exploration progress:
     *   steps < 3  → mostly Goblins
     *   steps 3–6  → Goblins and Trolls
     *   steps > 6  → Trolls and occasional DragonBoss
     */
    private Enemy spawnEnemy() {
        if (stepsExplored <= 3) {
            // Early game: almost always Goblin
            return new Goblin("Goblin Scout");
        }

        int roll = random.nextInt(100);

        if (stepsExplored <= 6) {
            if (roll < 55) {
                return new Goblin("Goblin Raider");
            }
            return new Troll("Cave Troll");
        }

        // Late game: mix of all three, with DragonBoss possible
        if (roll < 40) {
            return new Goblin("Goblin Champion");
        } else if (roll < 80) {
            return new Troll("Mountain Troll");
        }
        return new DragonBoss("Ancient Dragon");
    }

    /*
     * Post-combat resolution: grant XP/gold and maybe loot on win;
     * on loss the main loop exits via !player.isAlive().
     */
    private void resolveCombat(boolean won, Enemy enemy) {
        if (!won) {
            return; // game-over handled in start()
        }

        int gold = enemy.getGoldReward();
        int xp = enemy.getXpReward();
        player.addGold(gold);
        player.earnExperience(xp);
        System.out.println("\nVictory! You gained " + gold + " gold and " + xp + " XP.");

        // Random equipment drop
        if (random.nextDouble() < LOOT_DROP_CHANCE) {
            Equipment loot = rollLoot();
            inventory.addItem(loot);
            System.out.println("Loot found: " + loot.getName() + "!");
        }
    }

    /*
     * Creates a simple random weapon or armor drop for the inventory.
     */
    private Equipment rollLoot() {
        boolean weapon = random.nextBoolean();
        int bonus = 3 + random.nextInt(5); // 3–7

        if (weapon) {
            String[] names = { "Iron Blade", "Steel Dagger", "Enchanted Staff", "Hunter's Bow" };
            String name = names[random.nextInt(names.length)];
            return new Equipment(name, EquipmentSlot.WEAPON, bonus);
        } else {
            String[] names = { "Leather Vest", "Chain Mail", "Mystic Robe", "Plate Guard" };
            String name = names[random.nextInt(names.length)];
            return new Equipment(name, EquipmentSlot.ARMOR, bonus);
        }
    }


    /*
     * Shop menu
     * Purchases go into Inventory (equip them from the Inventory menu).
     */
    private void shopMenu() {
        System.out.println("\n--- Merchant of Orbis ---");
        System.out.println("Your gold: " + player.getGold());
        System.out.println("1. Orbis Blade   (+12 Attack)  — " + PRICE_ORBIS_BLADE + " gold");
        System.out.println("2. Aegis Plate   (+15 Max HP)  — " + PRICE_AEGIS_PLATE + " gold");
        System.out.println("3. Leave shop");
        int choice = InputValidator.getValidatedMenuChoice(scanner, 1, 3);

        if (choice == 3) {
            System.out.println("The merchant nods farewell.");
            return;
        }

        if (choice == 1) {
            buyItem("Orbis Blade", EquipmentSlot.WEAPON, 12, PRICE_ORBIS_BLADE);
        } else {
            buyItem("Aegis Plate", EquipmentSlot.ARMOR, 15, PRICE_AEGIS_PLATE);
        }
    }

    /*
     * Attempts a purchase: spend gold, then add the item to inventory.
     */
    private void buyItem(String name, EquipmentSlot slot, int bonus, int price) {
        if (!player.spendGold(price)) {
            System.out.println("Not enough gold. You need " + price + " (have " + player.getGold() + ").");
            System.out.println("Come back when you're richer, weakling.");
            return;
        }
        inventory.addItem(new Equipment(name, slot, bonus));
        System.out.println("Purchased " + name + "! (" + price + " gold spent, "
                + player.getGold() + " remaining)");
        System.out.println("Equip it from the Inventory menu.");
    }

    /*
     * Shows equipped gear + bag, then optionally equips a bag item by index.
     * Equipping a second item in the same slot replaces the old one.
     */
    private void inventoryMenu() {
        System.out.println("\n--- Inventory ---");
        inventory.displayInventory();

        if (inventory.isEmpty()) {
            return;
        }

        // Items are numbered from 1; size + 1 is Cancel
        int size = inventory.getSize();
        System.out.println((size + 1) + ". Cancel");
        System.out.println("Select a bag item to equip, or Cancel:");
        int choice = InputValidator.getValidatedMenuChoice(scanner, 1, size + 1);

        if (choice == size + 1) {
            System.out.println("Nothing changed.");
            return;
        }

        boolean equipped = inventory.equipItem(player, choice - 1);
        if (!equipped) {
            System.out.println("Could not equip that item.");
        }
    }
}
