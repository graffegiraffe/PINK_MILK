package by.rublevskaya.pinkmilk.log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.Assert.*;

public class CustomLoggerTest {

    private File tempLogFile;

    @Before
    public void setUp() throws IOException {
        tempLogFile = File.createTempFile("test-log", ".log");
        tempLogFile.deleteOnExit();
    }

    @After
    public void tearDown() {
        if (tempLogFile.exists()) {
            tempLogFile.delete();
        }
    }

    @Test
    public void testInfoLog() throws IOException {
        String testMessage = "Test INFO log message";
        CustomLogger.log("INFO", testMessage, null, tempLogFile.getAbsolutePath());
        try (BufferedReader reader = new BufferedReader(new FileReader(tempLogFile))) {
            String logEntry = reader.readLine();
            assertNotNull("Лог-файл пуст, хотя ожидалась запись", logEntry);
            assertTrue("Сообщение лога должно содержать уровень INFO", logEntry.contains("INFO"));
            assertTrue("Сообщение лога должно содержать текст сообщения", logEntry.contains(testMessage));
        }
    }

    @Test
    public void testWarningLog() throws IOException {
        String testMessage = "Test WARNING log message";
        CustomLogger.log("WARNING", testMessage, null, tempLogFile.getAbsolutePath());
        try (BufferedReader reader = new BufferedReader(new FileReader(tempLogFile))) {
            String logEntry = reader.readLine();
            assertNotNull("Лог-файл пуст, хотя ожидалась запись", logEntry);
            assertTrue("Сообщение лога должно содержать уровень WARNING", logEntry.contains("WARNING"));
            assertTrue("Сообщение лога должно содержать текст сообщения", logEntry.contains(testMessage));
        }
    }

    @Test
    public void testLogWithThrowable() throws IOException {
        String testMessage = "Test log with exception";
        Throwable testException = new RuntimeException("Test exception");
        CustomLogger.log("ERROR", testMessage, testException, tempLogFile.getAbsolutePath());
        StringBuilder logContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(tempLogFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logContent.append(line).append(System.lineSeparator());
            }
        }
        String fullLog = logContent.toString();
        assertTrue("Лог должен содержать уровень ERROR", fullLog.contains("ERROR"));
        assertTrue("Лог должен содержать текст сообщения", fullLog.contains(testMessage));
        assertTrue("Лог должен содержать стек-трейс исключения", fullLog.contains("RuntimeException"));
        assertTrue("Лог должен содержать сообщение исключения", fullLog.contains("Test exception"));
    }
}