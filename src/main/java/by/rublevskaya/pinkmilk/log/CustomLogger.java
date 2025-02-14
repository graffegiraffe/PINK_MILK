package by.rublevskaya.pinkmilk.log;

import by.rublevskaya.pinkmilk.service.PropsHandler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLogger {
    private static final String INFO_LOG_FILE = PropsHandler.getPropertyFromConfig("INFO_LOG_FILE");

    public static void log(String level, String message, Throwable throwable, String logFile) {

        if (logFile == null || logFile.isEmpty()) {
            System.err.println("Ошибка: Файл для логирования не задан");
            return;
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String logMessage = String.format("%s [%s] %s", timestamp, level.toUpperCase(), message);

        if (throwable != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            logMessage += "\n" + sw.toString();
        }

        try {
            File file = new File(logFile);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(logMessage + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в лог-файл " + logFile + " : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void info(String message) {
        log("INFO", message, null, INFO_LOG_FILE);
    }

    public static void warning(String message) {
        log("WARNING", message, null, INFO_LOG_FILE);
    }

}
