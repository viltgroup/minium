package com.vilt.minium.app;

import static com.google.common.io.Closeables.close;
import static java.lang.String.format;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class Configuration {

    // defaults
    static final String DEFAULT_LOOPBACK = "127.0.0.1";
    // Calculated using the formula: abs("minium".hashCode()) % 49152
    static final int DEFAULT_PORT = 18129;
    static final int DEFAULT_SHUTDOWN_PORT = 18130;
    
    // keys
    public static final String PORT_KEY = "minium.app.port";
    public static final String HOST_KEY = "minium.app.host";
    public static final String SHUTDOWN_PORT_KEY = "minium.app.shutdown.port";
    public static final String CHROME_BIN = "chrome.bin";

    private Properties properties;
    private File baseDir;

    public Configuration(File baseDir, File configurationFile) throws IOException {
        this.baseDir = baseDir;
        properties = new Properties();
        FileReader reader = null;
        
        try {
            reader = new FileReader(configurationFile);
            properties.load(reader);
        }
        catch(IOException e) {
            throw new IllegalStateException(format("Could not read configuration file %s", configurationFile));
        }
        finally {
            close(reader, true);
        }
    }

    public String getHost() {
        return getConfig(HOST_KEY, DEFAULT_LOOPBACK);
    }
    
    public int getPort() {
        return getConfigInt(PORT_KEY, DEFAULT_PORT);
    }

    public int getShutdownPort() {
        return getConfigInt(SHUTDOWN_PORT_KEY, DEFAULT_SHUTDOWN_PORT);
    }
    
    public File getChromeBin() {
        File chromeBin = getConfigAsFile(CHROME_BIN, null);
        if (chromeBin != null) return chromeBin;
        
        if (SystemUtils.IS_OS_WINDOWS) {
            String localAppData = System.getenv("LOCALAPPDATA");
            if (!StringUtils.isEmpty(localAppData)) {
                return new File(localAppData, "Google/Chrome/Application/chrome.exe");
            }
        }
        return null;
    }

    public File getBaseDir() {
        return baseDir;
    }
    
    public String getConfig(String key, String defaultVal) {
        return properties.getProperty(key, defaultVal);
    }

    public File getConfigAsFile(String key, File defaultVal) {
        String path = getConfig(key, null);
        return path == null || path.trim().isEmpty() ? null : new File(path);
    }
    
    public int getConfigInt(String key, int defaultVal) {
            String value = properties.getProperty(key);
        try {
            return value == null || value.trim().isEmpty() ? defaultVal : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.out.println(format("Property key %s value '%s' is not a valid integer", key, value));
            throw e;
        }
    }
}
