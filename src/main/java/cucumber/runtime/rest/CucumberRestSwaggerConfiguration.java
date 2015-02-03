package cucumber.runtime.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;

@Configuration
@EnableSwagger
@ConditionalOnClass(SpringSwaggerConfig.class)
public class CucumberRestSwaggerConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private SpringSwaggerConfig springSwaggerConfig;

    @Bean
    public SwaggerSpringMvcPlugin customImplementation() {
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
                .includePatterns(".*/cucumber/.*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/cucumber/ui/**").addResourceLocations("/cucumber/ui/", "classpath:/META-INF/resources/");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Cucumber REST API",
                "Enables Cucumber backends to be accessible through a REST service",
                "My Apps API terms of service",
                "rui.figueira@vilt-group.com",
                "My Apps API Licence Type",
                "My Apps API License URL");
        return apiInfo;
    }
}