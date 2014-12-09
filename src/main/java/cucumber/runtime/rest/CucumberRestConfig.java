package cucumber.runtime.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cucumber.runtime.Backend;

@Configuration
public class CucumberRestConfig {

    @Autowired
    private Map<String, Backend> backends;

    @Bean
    public CucumberRestController backendRestController() {
        return new CucumberRestController(backends);
    }
}
