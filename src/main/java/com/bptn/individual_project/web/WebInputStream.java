package com.bptn.individual_project.web;

import com.bptn.individual_project.util.MessageLogger;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class WebInputStream extends InputStream {
    private final BlockingQueue<String> inputs = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<String>> outputs = new LinkedBlockingQueue<>();
    private byte[] buffer = new byte[0];
    private int pos = 0;

    public void submitInput(String input) {
        inputs.add(input + "\n");
    }

    public List<String> waitForOutput() throws InterruptedException {
        return outputs.take();
    }

    @Override
    public int read() {
        if (pos >= buffer.length) {
            // We are about to block for input. This means the engine has finished processing
            // the previous input and is now waiting for the next action.
            // Flush the logs and send them to the output queue!
            List<String> logs = MessageLogger.getLogsAndClear();
            outputs.add(logs);

            try {
                String line = inputs.take(); // block until REST provides input
                buffer = line.getBytes();
                pos = 0;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return -1;
            }
        }
        return buffer[pos++];
    }
}
