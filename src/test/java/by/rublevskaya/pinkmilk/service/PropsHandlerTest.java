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
        String originalConfigPath = updateConfigFilePath(TEMP_CONFIG_FILE);
        String testProperty = "TEST_PROPERTY";
        String testValue = "TestValue";
        createTempConfigFile(testProperty + "=" + testValue);
        PropsHandler.reloadProperties();
        String actualValue = PropsHandler.getPropertyFromConfig(testProperty);
        assertEquals("Значение свойства должно совпадать с ожидаемым", testValue, actualValue);
        deleteTempConfigFile();
        restoreConfigFilePath(originalConfigPath);
    }

    @Test
    public void testGetNonExistingProperty() throws IOException {
        createTempConfigFile("");
        PropsHandler.reloadProperties();
        String actualValue = PropsHandler.getPropertyFromConfig("NON_EXISTENT_PROPERTY");
        assertNull("Для несуществующего свойства должно возвращаться значение null", actualValue);
        deleteTempConfigFile();
    }

    @Test
    public void testMissingConfigFile() throws IOException {
        deleteTempConfigFile();
        PropsHandler.reloadProperties();
        String actualValue = PropsHandler.getPropertyFromConfig("ANY_PROPERTY");
        assertNull("Если файл конфигурации отсутствует, значение должно быть null", actualValue);
    }

    private void createTempConfigFile(String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_CONFIG_FILE))) {
            writer.write(content);
        }
    }

    private void deleteTempConfigFile() throws IOException {
        Path path = Path.of(TEMP_CONFIG_FILE);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    private String updateConfigFilePath(String newPath) {
        String original = "src/main/resources/config.properties";
        PropsHandler.setConfigFilePath(newPath);
        return original;
    }

    private void restoreConfigFilePath(String originalPath) {
        PropsHandler.setConfigFilePath(originalPath);
    }
}