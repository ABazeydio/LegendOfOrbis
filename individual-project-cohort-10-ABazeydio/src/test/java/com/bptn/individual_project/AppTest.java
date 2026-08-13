package com.bptn.individual_project;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

/**
 * Test suite for App application entry point.
 * Tests that the application can start and complete a game session.
 */
public class AppTest {

    /**
     * Test: App main method starts and completes a game session.
     */
    @Test
    public void testMain() {
        // Provide input for character creation, quitting, and ending the game.
        String input = "AssassinA\n3\n5\ny\nn\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Verify that the application starts and completes without throwing an exception.
        assertDoesNotThrow(() -> App.main(new String[] {}));
    }

    /**
     * Test: App class can be instantiated.
     */
    @Test
    public void testAppInstantiation() {
        assertDoesNotThrow(() -> new App());
    }
}