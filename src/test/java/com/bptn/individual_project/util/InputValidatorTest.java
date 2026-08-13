package com.bptn.individual_project.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

/**
 * Comprehensive test suite for InputValidator utility class.
 * Tests all input validation methods with various edge cases and scenarios.
 * 
 * Complex Methods Tested:
 * 1. getValidatedMenuChoice() - Tests integer parsing, bounds checking, and retry logic
 * 2. getValidatedName() - Tests length validation, character validation, and trimming
 * 3. getYesNo() - Tests case-insensitive boolean parsing
 * 4. getValidatedDirection() - Tests single character direction validation
 */
public class InputValidatorTest {
    
    
    /**
     * getValidatedMenuChoice with valid input returns correct value.
     * Tests basic integer parsing within bounds.
     */
    @Test
    public void testGetValidatedMenuChoiceValid() {
        Scanner scanner = new Scanner("2\n");
        int result = InputValidator.getValidatedMenuChoice(scanner, 1, 5);
        assertEquals(2, result);
    }
    
    /**
     * getValidatedMenuChoice with value below and above minimum retries.
     * Tests that out-of-range values trigger retry logic.
     */
    @Test
    public void testGetValidatedMenuChoiceBelowMinMax() {
        Scanner scanner = new Scanner("0\n6\n3\n");
        int result = InputValidator.getValidatedMenuChoice(scanner, 1, 5);
        assertEquals(3, result);
    }
    
    
    /**
     * getValidatedMenuChoice with non-numeric input retries.
     */
    @Test
    public void testGetValidatedMenuChoiceNonNumeric() {
        Scanner scanner = new Scanner("abc\n3\n");
        int result = InputValidator.getValidatedMenuChoice(scanner, 1, 5);
        assertEquals(3, result);
    }
    
    
    /**
     * getValidatedName with valid name (2-20 chars, letters only).
     * Tests basic validation pass-through.
     */
    @Test
    public void testGetValidatedNameValid() {
        Scanner scanner = new Scanner("John\n");
        String result = InputValidator.getValidatedName(scanner);
        assertEquals("John", result);
    }
    

    /**
     * getValidatedName too short (< 2 chars) retries.
     * Tests length validation - too short.
     */
    @Test
    public void testGetValidatedNameTooShort() {
        Scanner scanner = new Scanner("J\nJohn\n");
        String result = InputValidator.getValidatedName(scanner);
        assertEquals("John", result);
    }
    
    /**
     * getValidatedName too long (> 20 chars) retries.
     * Tests length validation - too long.
     */
    @Test
    public void testGetValidatedNameTooLong() {
        Scanner scanner = new Scanner("JohnDoeTheGreatHeroXX\nJohn\n"); // 21 chars — over the 20 max
        String result = InputValidator.getValidatedName(scanner);
        assertEquals("John", result);
    }
    
    /**
     * getValidatedName with numbers (invalid) retries.
     * Tests character validation - numbers not allowed.
     */
    @Test
    public void testGetValidatedNameWithNumbers() {
        Scanner scanner = new Scanner("John123\nJohn\n");
        String result = InputValidator.getValidatedName(scanner);
        assertEquals("John", result);
    }
    
    /**
     * getValidatedName with special characters (invalid) retries.
     * Tests character validation - special chars not allowed.
     */
    @Test
    public void testGetValidatedNameWithSpecialChars() {
        Scanner scanner = new Scanner("John@#$\nJohn\n");
        String result = InputValidator.getValidatedName(scanner);
        assertEquals("John", result);
    }
    
    
    
    /**
     * Test: getYesNo with "Y" (uppercase) returns true.
     */
    @Test
    public void testGetYesNoYUppercase() {
        Scanner scanner = new Scanner("Y\n");
        boolean result = InputValidator.getYesNo(scanner, "Continue?");
        assertTrue(result);
    }
    
    /**
     * Test: getYesNo with "yes" returns true.
     */
    @Test
    public void testGetYesNoYes() {
        Scanner scanner = new Scanner("yes\n");
        boolean result = InputValidator.getYesNo(scanner, "Continue?");
        assertTrue(result);
    }


    /**
     * Test: getYesNo with "N" (uppercase) returns false.
     */
    @Test
    public void testGetYesNoNUppercase() {
        Scanner scanner = new Scanner("N\n");
        boolean result = InputValidator.getYesNo(scanner, "Continue?");
        assertFalse(result);
    }
    
    /**
     * Test: getYesNo with "no" returns false.
     */
    @Test
    public void testGetYesNoNo() {
        Scanner scanner = new Scanner("no\n");
        boolean result = InputValidator.getYesNo(scanner, "Continue?");
        assertFalse(result);
    }
    
    /**
     * Test: getYesNo with invalid input retries.
     */
    @Test
    public void testGetYesNoInvalid() {
        Scanner scanner = new Scanner("maybe\nyes\n");
        boolean result = InputValidator.getYesNo(scanner, "Continue?");
        assertTrue(result);
    }
    
    
    /**
     * Test: getValidatedDirection with "N" returns 'N'.
     */
    @Test
    public void testGetValidatedDirectionN() {
        Scanner scanner = new Scanner("N\n");
        char result = InputValidator.getValidatedDirection(scanner);
        assertEquals('N', result);
    }
    

    /**
     * Test: getValidatedDirection with "E" returns 'E'.
     */
    @Test
    public void testGetValidatedDirectionE() {
        Scanner scanner = new Scanner("E\n");
        char result = InputValidator.getValidatedDirection(scanner);
        assertEquals('E', result);
    }
    
    /**
     * Test: getValidatedDirection with "S" returns 'S'.
     */
    @Test
    public void testGetValidatedDirectionS() {
        Scanner scanner = new Scanner("S\n");
        char result = InputValidator.getValidatedDirection(scanner);
        assertEquals('S', result);
    }
    
    /**
     * Test: getValidatedDirection with "W" returns 'W'.
     */
    @Test
    public void testGetValidatedDirectionW() {
        Scanner scanner = new Scanner("W\n");
        char result = InputValidator.getValidatedDirection(scanner);
        assertEquals('W', result);
    }
    
    /**
     * Test: getValidatedDirection with invalid direction retries.
     */
    @Test
    public void testGetValidatedDirectionInvalid() {
        Scanner scanner = new Scanner("X\nE\n");
        char result = InputValidator.getValidatedDirection(scanner);
        assertEquals('E', result);
    }
    
    /**
     * Test: getValidatedDirection with multi-character input retries.
     */
    @Test
    public void testGetValidatedDirectionMultiChar() {
        Scanner scanner = new Scanner("NORTH\nS\n");
        char result = InputValidator.getValidatedDirection(scanner);
        assertEquals('S', result);
    }
}
