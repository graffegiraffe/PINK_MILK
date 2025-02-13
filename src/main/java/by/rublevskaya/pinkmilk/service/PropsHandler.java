package by.rublevskaya.pinkmilk.service;

import by.rublevskaya.pinkmilk.log.CustomLogger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropsHandler {
    private static String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            CustomLogger.warning("Failed to load properties from " + CONFIG_FILE_PATH);
        }
    }

    public static String getPropertyFromConfig(String propName) {
        return properties.getProperty(propName);
    }
    public static void setConfigFilePath(String filePath) {
        CONFIG_FILE_PATH = filePath;
    }

    public static void reloadProperties() {
        properties = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            CustomLogger.warning("Failed to reload properties from " + CONFIG_FILE_PATH);
        }
    }

}
