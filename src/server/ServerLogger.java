package server;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerLogger {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        System.out.println(timestamp + " - INFO: " + message);
    }

    public static void logError(String errorMessage) {
        String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
        System.err.println(timestamp + " - ERROR: " + errorMessage);
    }
}
