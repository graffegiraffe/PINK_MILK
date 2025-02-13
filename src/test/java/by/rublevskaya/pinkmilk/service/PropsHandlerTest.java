package by.rublevskaya.pinkmilk.service;

import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class PropsHandlerTest {

    private static final String TEMP_CONFIG_FILE = "src/main/resources/config_test.properties";

    @Test
    public void testGetExistingProperty() throws IOException {
        // Arrange
        String originalConfigPath = updateConfigFilePath(TEMP_CONFIG_FILE); // Изменяем путь к конфигурационному файлу
        String testProperty = "TEST_PROPERTY";
        String testValue = "TestValue";
        createTempConfigFile(testProperty + "=" + testValue);

        // Act
        PropsHandler.reloadProperties(); // Перезагружаем свойства
        String actualValue = PropsHandler.getPropertyFromConfig(testProperty);

        // Assert
        assertEquals("Значение свойства должно совпадать с ожидаемым", testValue, actualValue);

        // Cleanup
        deleteTempConfigFile();
        restoreConfigFilePath(originalConfigPath); // Возвращаем оригинальный путь
    }

    @Test
    public void testGetNonExistingProperty() throws IOException {
        // Arrange
        createTempConfigFile("");

        // Act
        PropsHandler.reloadProperties();
        String actualValue = PropsHandler.getPropertyFromConfig("NON_EXISTENT_PROPERTY");

        // Assert
        assertNull("Для несуществующего свойства должно возвращаться значение null", actualValue);

        // Cleanup
        deleteTempConfigFile();
    }

    @Test
    public void testMissingConfigFile() throws IOException {
        // Arrange
        deleteTempConfigFile(); // Убедимся, что файл конфигурации отсутствует

        // Act
        PropsHandler.reloadProperties();
        String actualValue = PropsHandler.getPropertyFromConfig("ANY_PROPERTY");

        // Assert
        assertNull("Если файл конфигурации отсутствует, значение должно быть null", actualValue);
    }

    /**
     * Создает временный файл конфигурации для тестов.
     */
    private void createTempConfigFile(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_CONFIG_FILE))) {
            writer.write(content);
        }
    }

    /**
     * Удаляет временный файл конфигурации после тестов.
     */
    private void deleteTempConfigFile() throws IOException {
        Path path = Path.of(TEMP_CONFIG_FILE);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    /**
     * Изменяет путь конфигурационного файла для тестов.
     *
     * @param newPath Новый путь к конфигурационному файлу
     * @return Оригинальный путь к конфигурационному файлу
     */
    private String updateConfigFilePath(String newPath) {
        String original = "src/main/resources/config.properties"; // Оригинальный путь из PropsHandler
        PropsHandler.setConfigFilePath(newPath);
        return original;
    }

    /**
     * Восстанавливает оригинальный путь к конфигурационному файлу.
     *
     * @param originalPath Оригинальный путь
     */
    private void restoreConfigFilePath(String originalPath) {
        PropsHandler.setConfigFilePath(originalPath);
    }
}