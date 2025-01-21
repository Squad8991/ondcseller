package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try {
            // Adjust the file path to point to the configurations folder
            FileInputStream inputStream = new FileInputStream("Configurations/config.properties");
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            System.err.println("Error loading config file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getURL() {
        String url = properties.getProperty("url");
        if (url == null) {
            System.err.println("URL not found in config.properties, using default URL.");
            return "https://admin.squadcube.in/login";
        }
        return url;
    }

    public String getUsername() {
        String username = properties.getProperty("username");
        if (username == null) {
            throw new RuntimeException("Username not found in config.properties!");
        }
        return username;
    }

    public String getPassword() {
        String password = properties.getProperty("password");
        if (password == null) {
            throw new RuntimeException("Password not found in config.properties!");
        }
        return password;
    }

    public String getDriverPath() {
        String driverPath = properties.getProperty("driverPath");
        if (driverPath == null) {
            throw new RuntimeException("Driver path not found in config.properties!");
        }
        return driverPath;
    }
}
