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
        // Создаем временный файл для логов
        tempLogFile = File.createTempFile("test-log", ".log");
        tempLogFile.deleteOnExit(); // Удаляем файл после завершения тестов
    }

    @After
    public void tearDown() {
        // Удаляем временный файл, если он существует
        if (tempLogFile.exists()) {
            tempLogFile.delete();
        }
    }

    @Test
    public void testInfoLog() throws IOException {
        // Arrange: сообщение для логирования
        String testMessage = "Test INFO log message";

        // Act: вызываем метод info, используя путь к временному файлу
        CustomLogger.log("INFO", testMessage, null, tempLogFile.getAbsolutePath());

        // Assert: проверяем содержимое лог-файла
        try (BufferedReader reader = new BufferedReader(new FileReader(tempLogFile))) {
            String logEntry = reader.readLine();
            assertNotNull("Лог-файл пуст, хотя ожидалась запись", logEntry);
            assertTrue("Сообщение лога должно содержать уровень INFO", logEntry.contains("INFO"));
            assertTrue("Сообщение лога должно содержать текст сообщения", logEntry.contains(testMessage));
        }
    }

    @Test
    public void testWarningLog() throws IOException {
        // Arrange: сообщение для логирования
        String testMessage = "Test WARNING log message";

        // Act: вызываем метод warning, используя путь к временному файлу
        CustomLogger.log("WARNING", testMessage, null, tempLogFile.getAbsolutePath());

        // Assert: проверяем содержимое лог-файла
        try (BufferedReader reader = new BufferedReader(new FileReader(tempLogFile))) {
            String logEntry = reader.readLine();
            assertNotNull("Лог-файл пуст, хотя ожидалась запись", logEntry);
            assertTrue("Сообщение лога должно содержать уровень WARNING", logEntry.contains("WARNING"));
            assertTrue("Сообщение лога должно содержать текст сообщения", logEntry.contains(testMessage));
        }
    }

    @Test
    public void testLogWithThrowable() throws IOException {
        // Arrange: создаем исключение для логирования
        String testMessage = "Test log with exception";
        Throwable testException = new RuntimeException("Test exception");

        // Act: вызываем метод log, записывающий сообщение с исключением
        CustomLogger.log("ERROR", testMessage, testException, tempLogFile.getAbsolutePath());

        // Assert: проверяем содержимое всего лог-файла
        StringBuilder logContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(tempLogFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logContent.append(line).append(System.lineSeparator());
            }
        }
        String fullLog = logContent.toString();

        // Убедиться, что лог содержит требуемую информацию
        assertTrue("Лог должен содержать уровень ERROR", fullLog.contains("ERROR"));
        assertTrue("Лог должен содержать текст сообщения", fullLog.contains(testMessage));
        assertTrue("Лог должен содержать стек-трейс исключения", fullLog.contains("RuntimeException"));
        assertTrue("Лог должен содержать сообщение исключения", fullLog.contains("Test exception"));
    }
}