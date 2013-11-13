package com.vilt.minium.webconsole.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationController.class);

    private Properties properties = new Properties();
    
    @RequestMapping(method = GET)
    @ResponseBody
    public Map<String, Object> getConfiguration() {
        Map<String, Object> config = Maps.newHashMap();
        config.put("enableFileMenu", Boolean.parseBoolean(properties.getProperty("minium.app.enableFileMenu")));
        return config;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String miniumHome = System.getProperty("minium.home");
        if (miniumHome == null) return;
        
        File configFile = new File(miniumHome, "app.properties");
        if (configFile.exists()) {
            FileInputStream is = null;
            try {
                is = new FileInputStream(configFile);
                properties.load(is);
            } catch (Exception e) {
                // oh well
                logger.warn("Could not read configuration file", e);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
    }
}
