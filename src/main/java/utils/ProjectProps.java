package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ProjectProps {

    public static String getBaseUrl() {
        try {
            FileInputStream inputBuffer = new FileInputStream("src/main/resources/config.properties");

            Properties properties = new Properties();
            properties.load(inputBuffer);

            inputBuffer.close();

            return properties.getProperty("baseUrl");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
