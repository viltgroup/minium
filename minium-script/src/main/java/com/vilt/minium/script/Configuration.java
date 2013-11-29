package com.vilt.minium.script;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Configuration {
    
    private static final String MINIUM_HOME_KEY = "minium.home";

    private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

    private static class LazyHolder {
        private static Configuration INSTANCE = new Configuration();
    }

    public static Configuration getInstance() {
        return LazyHolder.INSTANCE;
    }

    private Properties config = new Properties();
    
    public Configuration() {
        loadConfiguration();
    }
    
    public String get(String key) {
        return config.getProperty(key);
    }
    
    protected void loadConfiguration() {
        // try to load configuration
        String miniumBaseDir = System.getProperty(MINIUM_HOME_KEY);
        if (!StringUtils.isEmpty(miniumBaseDir)) {
            File configFile = new File(miniumBaseDir, "app.properties");
            if (configFile.exists()) {
                FileInputStream is = null;
                try {
                    is = new FileInputStream(configFile);
                    config.load(is);
                } catch (Exception e) {
                    // oh well
                    logger.warn("Could not read configuration file", e);
                } finally {
                    IOUtils.closeQuietly(is);
                }
            }
        }
    }

    public List<String> getAsPath(String key) {
        String pathStr = config.getProperty(key);
        if (StringUtils.isEmpty(pathStr)) return Collections.emptyList();
        
        return Arrays.asList(StringUtils.split(pathStr, File.pathSeparatorChar));
    }
}
