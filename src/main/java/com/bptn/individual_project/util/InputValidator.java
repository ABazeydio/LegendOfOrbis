package com.bptn.individual_project.util;

import java.util.Scanner;

/*
 * Centralized input validation utility for Legends of Orbis.
 *
 * Design: all methods are static — InputValidator holds no state between calls,
 * so there is no reason to instantiate it. Callers just pass their Scanner in
 */
public class InputValidator {

    // Private constructor — prevents accidental instantiation of this utility class
    private InputValidator() {}

    /*
     * Handles two error cases:
     *   - Non-numeric input (e.g. "abc") — caught by NumberFormatException
     *   - Out-of-range integer (e.g. "99") — caught by bounds check
     *
     * Used for every numbered menu in the game: class selection, main menu,
     * combat actions, inventory selection.
     */
    public static int getValidatedMenuChoice(Scanner scanner, int min, int max) {
        while (true) {
            com.bptn.individual_project.util.MessageLogger.print("Enter choice (" + min + "-" + max + "): ");
            String input = com.bptn.individual_project.web.WebSessionContext.getNextLine().trim();

            try {
                int choice = Integer.parseInt(input);
                if (choice >= min && choice <= max) {
                    return choice;
                }
                com.bptn.individual_project.util.MessageLogger.println("  Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                // Input was not a number at all
                com.bptn.individual_project.util.MessageLogger.println("  Invalid input — please enter a number.");
            }
        }
    }


    /*
     * Prompts the user to enter a character name and validates it against:
     *   - Length: 2 to 20 characters
     *   - Characters: letters and spaces only (no numbers or symbols)
     *
     * Loops until a valid name is entered.
     */
    public static String getValidatedName(Scanner scanner) {
        while (true) {
            com.bptn.individual_project.util.MessageLogger.print("Enter your character's name: ");
            String input = com.bptn.individual_project.web.WebSessionContext.getNextLine().trim();

            if (input.length() < 2 || input.length() > 20) {
                com.bptn.individual_project.util.MessageLogger.println("  Name must be between 2 and 20 characters.");
                continue;
            }

            if (!input.matches("[a-zA-Z ]+")) {
                com.bptn.individual_project.util.MessageLogger.println("  Name can only contain letters and spaces.");
                continue;
            }

            return input;
        }
    }

    
    /*
     * Prompts the user for a yes/no answer.
     * Accepts: y, yes, n, no (case-insensitive).
     * Loops until a valid response is entered.
     * Returns true for yes, false for no.
     *
     * Used for: "Play again? (Y/N)", "Quit game? (Y/N)", etc.
     */
    public static boolean getYesNo(Scanner scanner, String prompt) {
        while (true) {
            com.bptn.individual_project.util.MessageLogger.print(prompt + " (Y/N): ");
            String input = com.bptn.individual_project.web.WebSessionContext.getNextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                com.bptn.individual_project.util.MessageLogger.println("  Please enter Y or N.");
            }
        }
    }


    /*
     * Prompts the player to choose an exploration direction.
     * Valid inputs: N, E, S, W (case-insensitive).
     * Loops until a valid single-character direction is entered.
     * Returns the direction as an uppercase character.
     *
     * Used by GameEngine's exploration/movement flow.
     */
    public static char getValidatedDirection(Scanner scanner) {
        while (true) {
            com.bptn.individual_project.util.MessageLogger.print("Choose a direction to explore (N / E / S / W): ");
            String input = com.bptn.individual_project.web.WebSessionContext.getNextLine().trim().toUpperCase();

            if (input.length() == 1 && "NESW".contains(input)) {
                return input.charAt(0);
            }
            com.bptn.individual_project.util.MessageLogger.println("  Please enter N, E, S, or W.");
        }
    }
}
