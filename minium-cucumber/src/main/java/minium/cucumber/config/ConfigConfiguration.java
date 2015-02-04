package minium.cucumber.config;

import minium.script.js.JsVariable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigConfiguration {

    @Bean
    @ConfigurationProperties("minium.config")
    @JsVariable("config")
    public ConfigProperties config() {
        return new ConfigProperties();
    }
}
