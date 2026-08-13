package com.bptn.individual_project.web;

import com.bptn.individual_project.engine.GameEngine;
import com.bptn.individual_project.util.MessageLogger;

import java.util.List;
import java.util.Scanner;

public class GameSession {
    private final String id;
    private final WebInputStream webInputStream;
    private final Thread engineThread;

    public GameSession(String id) {
        this.id = id;
        this.webInputStream = new WebInputStream();
        
        this.engineThread = new Thread(() -> {
            Scanner scanner = new Scanner(webInputStream);
            new GameEngine(scanner).start();
            // If the engine loop ever fully exits, flush final logs just in case.
            try {
                // Submit one final output so the waiting REST call isn't hung forever
                webInputStream.submitInput(""); // mock input
            } catch (Exception e) {}
        });
        this.engineThread.start();
    }

    public String getId() {
        return id;
    }

    public List<String> waitForOutput() throws InterruptedException {
        return webInputStream.waitForOutput();
    }

    public void submitInput(String input) {
        webInputStream.submitInput(input);
    }
}
