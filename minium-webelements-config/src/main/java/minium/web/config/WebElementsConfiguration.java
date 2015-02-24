package minium.web.config;

import static minium.web.internal.WebModules.combine;
import static minium.web.internal.WebModules.debugModule;
import static minium.web.internal.WebModules.defaultModule;

import java.util.List;

import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.config.services.DriverServicesProperties;
import minium.web.internal.WebElementsFactory;
import minium.web.internal.WebElementsFactory.Builder;
import minium.web.internal.WebModule;
import minium.web.internal.WebModules;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@EnableConfigurationProperties
public class WebElementsConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "minium.webdriver")
    public WebDriverProperties webDriverProperties() {
        return new WebDriverProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "minium.driverservices")
    public DriverServicesProperties driverServicesProperties() {
        return new DriverServicesProperties();
    }

    @Autowired
    @Bean
    public WebDriverFactory webDriverFactory(DriverServicesProperties driverServicesProperties) {
        return new WebDriverFactory(driverServicesProperties);
    }

    @Autowired
    @Bean(destroyMethod = "quit")
    public WebDriver wd(WebDriverFactory webDriverFactory, WebDriverProperties webDriverProperties) {
        return webDriverFactory.create(webDriverProperties);
    }

    @Autowired
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebModule defaultWebModule(WebDriver wd) {
        return combine(defaultModule(wd), debugModule());
    }

    @Autowired
    @Bean
    public WebElementsFactory<DefaultWebElements> elementsFactory(List<WebModule> webModules) {
        WebModule combinedWebModule = WebModules.combine(webModules);
        Builder<DefaultWebElements> builder = new WebElementsFactory.Builder<>();
        combinedWebModule.configure(builder);
        return builder.build();
    }
}
