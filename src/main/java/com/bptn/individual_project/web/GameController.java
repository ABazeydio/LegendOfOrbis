package com.bptn.individual_project.web;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api")
public class GameController {
    
    private final Map<String, GameSession> sessions = new ConcurrentHashMap<>();

    @PostMapping("/start")
    public GameResponse startGame() {
        String sessionId = UUID.randomUUID().toString();
        GameSession session = new GameSession(sessionId);
        sessions.put(sessionId, session);
        
        try {
            List<String> initialOutput = session.waitForOutput();
            return new GameResponse(sessionId, initialOutput);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new GameResponse(sessionId, List.of("Error starting session"));
        }
    }

    @PostMapping("/action")
    public GameResponse processAction(@RequestBody GameActionRequest request) {
        GameSession session = sessions.get(request.getSessionId());
        if (session == null) {
            return new GameResponse(request.getSessionId(), List.of("Session not found or expired. Please start a new game."));
        }
        
        session.submitInput(request.getInput());
        
        try {
            List<String> output = session.waitForOutput();
            return new GameResponse(request.getSessionId(), output);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new GameResponse(request.getSessionId(), List.of("Error processing action"));
        }
    }

    public static class GameActionRequest {
        private String sessionId;
        private String input;

        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        public String getInput() { return input; }
        public void setInput(String input) { this.input = input; }
    }

    public static class GameResponse {
        private String sessionId;
        private List<String> output;

        public GameResponse(String sessionId, List<String> output) {
            this.sessionId = sessionId;
            this.output = output;
        }

        public String getSessionId() { return sessionId; }
        public void setSessionId(String sessionId) { this.sessionId = sessionId; }
        public List<String> getOutput() { return output; }
        public void setOutput(List<String> output) { this.output = output; }
    }
}
