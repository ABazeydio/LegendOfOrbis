package com.bptn.individual_project.util;

import java.util.ArrayList;
import java.util.List;

public class MessageLogger {
    private static final ThreadLocal<List<String>> log = ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<StringBuilder> currentLine = ThreadLocal.withInitial(StringBuilder::new);

    public static void print(String message) {
        currentLine.get().append(message);
    }

    public static void println(String message) {
        log.get().add(currentLine.get().toString() + message);
        currentLine.get().setLength(0); // Reset for the next line
    }

    public static void println() {
        log.get().add(currentLine.get().toString());
        currentLine.get().setLength(0);
    }

    public static void printf(String format, Object... args) {
        print(String.format(format, args));
    }

    public static List<String> getLogsAndClear() {
        if (currentLine.get().length() > 0) {
            println();
        }
        List<String> currentLogs = new ArrayList<>(log.get());
        log.get().clear();
        return currentLogs;
    }
}
