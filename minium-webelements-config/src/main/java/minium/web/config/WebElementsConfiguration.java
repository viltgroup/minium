package minium.web.config;

import static minium.web.WebModules.baseModule;
import static minium.web.WebModules.combine;
import static minium.web.WebModules.conditionalModule;
import static minium.web.WebModules.interactableModule;
import static minium.web.WebModules.positionModule;

import java.util.List;

import minium.actions.debug.DebugInteractionPerformer;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.WebElementsFactory;
import minium.web.WebElementsFactory.Builder;
import minium.web.WebModule;
import minium.web.WebModules;
import minium.web.internal.actions.WebDebugInteractionPerformer;
import minium.web.internal.actions.WebInteractionPerformer;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.google.common.collect.ImmutableList;

@Configuration
@EnableConfigurationProperties
public class WebElementsConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "minium.webdriver")
    public WebDriverProperties webDriverProperties() {
        return new WebDriverProperties();
    }

    @Autowired
    @Bean(destroyMethod = "quit")
    public WebDriver wd(WebDriverProperties webDriverProperties) {
        return new WebDriverFactory().create(webDriverProperties);
    }

    @Bean
    public WebInteractionPerformer interactionPerformer() {
        return new WebDebugInteractionPerformer();
    }

    @Autowired
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebModule defaultWebModule(WebDriver wd, WebInteractionPerformer performer) {
        ImmutableList.Builder<WebModule> webModules = ImmutableList.<WebModule>builder().add(baseModule(wd), positionModule(), conditionalModule(), interactableModule(performer));
        if (performer instanceof DebugInteractionPerformer) {
            webModules.add(WebModules.debugModule((DebugInteractionPerformer) performer));
        }
        return combine(baseModule(wd), positionModule(), conditionalModule(), interactableModule(performer));
    }

    @Autowired
    @Bean
    public WebElementsFactory<DefaultWebElements> elementsFactory(List<WebModule> webModules) {
        WebModule combinedWebModule = WebModules.combine(webModules);
        Builder<DefaultWebElements> builder = new WebElementsFactory.Builder<>();
        combinedWebModule.configure(builder);
        return builder.build();
    }

    @Autowired
    @Bean
    public DefaultWebElements root(WebElementsFactory<DefaultWebElements> elementsFactory) {
        return elementsFactory.createRoot();
    }

}
