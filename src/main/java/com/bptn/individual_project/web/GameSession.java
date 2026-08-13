package com.bptn.individual_project.web;

import com.bptn.individual_project.engine.GameEngine;
import com.bptn.individual_project.util.MessageLogger;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GameSession {
    private final String id;
    private final BlockingQueue<String> inputs = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<String>> outputs = new LinkedBlockingQueue<>();
    private final Thread engineThread;

    public GameSession(String id) {
        this.id = id;
        
        this.engineThread = new Thread(() -> {
            WebSessionContext.initialize(inputs, outputs);
            // We pass null for Scanner since we no longer use it in InputValidator
            new GameEngine(null).start();
            // If the engine loop ever fully exits, flush final logs just in case.
            try {
                List<String> finalLogs = MessageLogger.getLogsAndClear();
                outputs.add(finalLogs);
            } catch (Exception e) {}
        });
        this.engineThread.start();
    }

    public String getId() {
        return id;
    }

    public List<String> waitForOutput() throws InterruptedException {
        return outputs.take();
    }

    public void submitInput(String input) {
        inputs.add(input);
    }
}
