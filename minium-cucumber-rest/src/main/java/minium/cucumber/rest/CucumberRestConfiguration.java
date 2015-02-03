package minium.cucumber.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CucumberRestConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private List<BackendConfigurer> backendConfigurers;

    @Autowired
    @Bean
    public CucumberRestController cucumberRestController() {
        BackendRegistry registry = new BackendRegistry();
        for (BackendConfigurer backendConfigurer : backendConfigurers) {
            backendConfigurer.addBackends(registry);
        }
        return new CucumberRestController(registry);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

}
