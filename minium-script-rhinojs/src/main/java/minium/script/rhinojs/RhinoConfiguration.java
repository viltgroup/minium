package minium.script.rhinojs;

import java.io.IOException;
import java.util.List;

import minium.script.js.JsVariable;
import minium.script.js.JsVariablePostProcessor;
import minium.script.rhinojs.RhinoProperties.RequireProperties;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.Browser;
import minium.web.actions.WebDriverBrowser;
import minium.web.config.WebDriverFactory;
import minium.web.internal.WebModule;
import minium.web.internal.WebModules;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@EnableConfigurationProperties(RhinoProperties.class)
public class RhinoConfiguration {

    @Bean
    public JsVariablePostProcessor jsVariablePostProcessor() {
        return new JsVariablePostProcessor();
    }

    @Bean
    public WebModule rhinoWebModule() {
        return RhinoWebModules.rhinoModule();
    }

    @Bean
    @ConfigurationProperties(prefix = "minium.rhino")
    public RhinoProperties rhinoProperties() {
        RequireProperties require = new RequireProperties();
        RhinoProperties rhinoProperties = new RhinoProperties();
        rhinoProperties.setRequire(require);
        return rhinoProperties;
    }

    @Autowired(required = false)
    @Bean
    public RhinoEngine rhinoEngine(RhinoProperties rhinoProperties) throws IOException {
        return new RhinoEngine(rhinoProperties);
    }

    @Autowired
    @Bean
    @JsVariable(
            value = "__browserFactory",
            expression = "require('minium').__browserFactory = __browserFactory;",
            deleteAfterExpression = true)
    @DependsOn("browser")
    public RhinoBrowserFactory rhinoBrowserFactory(RhinoEngine engine, WebDriverFactory webDriverFactory) {
        return new RhinoBrowserFactory(engine, webDriverFactory);
    }

    @Autowired
    @Bean
    @JsVariable(
            value = "__browser",
            expression = "minium = require('minium'); $ = minium.$; if (typeof minium.browser !== 'undefined') browser = minium.browser;",
            deleteAfterExpression = true)
    public Browser<DefaultWebElements> browser(WebDriver webDriver, List<WebModule> modules) {
        WebModule combinedWebModule = WebModules.combine(modules);
        return new WebDriverBrowser<DefaultWebElements>(webDriver, DefaultWebElements.class, combinedWebModule);
    }

    // nasty trick to load console module... This kind of stuff will eventually disappear...
    @Bean
    @JsVariable(
            value = "__console",
            expression = "console = require('console');",
            deleteAfterExpression = true)
    public Object console() {
        return new Object();
    }
}
