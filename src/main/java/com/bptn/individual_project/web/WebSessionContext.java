package com.bptn.individual_project.web;

import com.bptn.individual_project.util.MessageLogger;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WebSessionContext {
    private static final ThreadLocal<BlockingQueue<String>> inputQueue = new ThreadLocal<>();
    private static final ThreadLocal<BlockingQueue<List<String>>> outputQueue = new ThreadLocal<>();

    public static void initialize(BlockingQueue<String> in, BlockingQueue<List<String>> out) {
        inputQueue.set(in);
        outputQueue.set(out);
    }

    public static String getNextLine() {
        // We are about to block for input. Flush logs to output queue.
        List<String> logs = MessageLogger.getLogsAndClear();
        outputQueue.get().add(logs);
        try {
            return inputQueue.get().take(); // block until REST provides input
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "";
        }
    }
}
