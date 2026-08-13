package com.bptn.individual_project;

import java.util.Scanner;

import com.bptn.individual_project.engine.GameEngine;

/*
 * Application entry point for Legends of Orbis.
 * Creates a shared Scanner and hands control to GameEngine.
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // GameEngine owns the play session; App stays a thin launcher
        new GameEngine(scanner).start();
        scanner.close();
    }
}
