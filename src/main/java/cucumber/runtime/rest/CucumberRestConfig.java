package cucumber.runtime.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cucumber.runtime.Backend;

@Configuration
@EnableWebMvc
public class CucumberRestConfig extends WebMvcConfigurerAdapter {

    @Autowired
    @Bean
    public CucumberRestController cucumberRestController(Map<String, Backend> backends) {
        return new CucumberRestController(backends);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
