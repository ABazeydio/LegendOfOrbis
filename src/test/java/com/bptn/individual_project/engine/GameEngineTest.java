package com.bptn.individual_project.engine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.bptn.individual_project.character.Assassin;
import com.bptn.individual_project.character.Race;
import com.bptn.individual_project.enemy.Enemy;
import com.bptn.individual_project.item.Equipment;
import com.bptn.individual_project.item.EquipmentSlot;

class GameEngineTest {

    // Calls the private spawnEnemy() method.
    private Enemy spawnEnemy(GameEngine engine) throws Exception {
        var method = GameEngine.class.getDeclaredMethod("spawnEnemy");
        method.setAccessible(true);
        return (Enemy) method.invoke(engine);
    }

    /*
     * Tests that the game can start, create a character, and quit.
     */
    @Test
    void start_createsCharacterAndQuits() {
        Scanner scanner = new Scanner("Hero\n1\n5\ny\nn\n");
        GameEngine engine = new GameEngine(scanner);

        assertDoesNotThrow(() -> engine.start());
    }

    /*
     * Tests that the player can open the inventory and quit.
     */
    @Test
    void start_canOpenInventory() {
        Scanner scanner = new Scanner("Hero\n1\n2\n5\ny\nn\n");
        GameEngine engine = new GameEngine(scanner);

        assertDoesNotThrow(() -> engine.start());
    }

    /*
     * Tests that the player can open and leave the shop.
     */
    @Test
    void start_canOpenShop() {
        Scanner scanner = new Scanner("Hero\n1\n3\n3\n5\ny\nn\n");
        GameEngine engine = new GameEngine(scanner);

        assertDoesNotThrow(() -> engine.start());
    }

    /*
     * Tests that the player can view their stats and quit.
     */
    @Test
    void start_canViewStats() {
        Scanner scanner = new Scanner("Hero\n1\n4\n5\ny\nn\n");
        GameEngine engine = new GameEngine(scanner);

        assertDoesNotThrow(() -> engine.start());
    }

    /*
     * Tests that Mage character creation works.
     */
    @Test
    void start_createsMage() {
        Scanner scanner = new Scanner("Mage\n2\n5\ny\nn\n");
        GameEngine engine = new GameEngine(scanner);

        assertDoesNotThrow(() -> engine.start());
    }

    /*
     * Tests that loot generation creates equipment.
     */
    @Test
    void rollLoot_createsEquipment() throws Exception {
        GameEngine engine = new GameEngine(new Scanner(""));

        var method = GameEngine.class.getDeclaredMethod("rollLoot");
        method.setAccessible(true);

        Equipment loot = (Equipment) method.invoke(engine);

        assertNotNull(loot);
    }

    /*
     * Tests that enemy spawning creates an enemy.
     */
    @Test
    void spawnEnemy_createsEnemy() throws Exception {
        GameEngine engine = new GameEngine(new Scanner(""));

        Enemy enemy = spawnEnemy(engine);

        assertNotNull(enemy);
    }

    
    
    /*
     * The tests that the player can buy an item from the shop without throwing an exception.
     */
    @Test
    void buyItem_doesNotThrow() throws Exception {
        GameEngine engine = new GameEngine(new Scanner(""));

        // Access the private player field.
        var playerField = GameEngine.class.getDeclaredField("player");
        playerField.setAccessible(true);
        playerField.set(engine, new Assassin("Hero", 100, 20, Race.HUMAN));

        // Access and call the private buyItem() method.
        var method = GameEngine.class.getDeclaredMethod(
            "buyItem", String.class, EquipmentSlot.class, int.class, int.class
        );
        method.setAccessible(true);

        assertDoesNotThrow(() ->
            method.invoke(engine, "Sword", EquipmentSlot.WEAPON, 10, 50)
        );
    }
}
