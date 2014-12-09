package cucumber.runtime.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import cucumber.runtime.Backend;
import cucumber.runtime.rest.CucumberRestController;

@Configuration
@EnableWebMvc
public class TestRestWebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    @Bean
    public CucumberRestController remoteBackendService(Backend backend) {
        return new CucumberRestController(backend);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}