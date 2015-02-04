package minium.web.config;

import static java.lang.String.format;
import static minium.web.WebModules.baseModule;
import static minium.web.WebModules.combine;
import static minium.web.WebModules.conditionalModule;
import static minium.web.WebModules.interactableModule;
import static minium.web.WebModules.positionModule;

import java.util.List;
import java.util.Set;

import minium.Elements;
import minium.Minium;
import minium.actions.debug.DebugInteractionPerformer;
import minium.script.js.JsVariable;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.WebElementsFactory;
import minium.web.WebElementsFactory.Builder;
import minium.web.WebFinder;
import minium.web.WebModule;
import minium.web.WebModules;
import minium.web.config.WebDriverProperties.DimensionProperties;
import minium.web.config.WebDriverProperties.PointProperties;
import minium.web.config.WebDriverProperties.WindowProperties;
import minium.web.internal.actions.WebDebugInteractionPerformer;
import minium.web.internal.actions.WebInteractionPerformer;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

@Configuration
@EnableConfigurationProperties
public class WebDriverConfiguration {

    enum WebDriverType {
        CHROME(BrowserType.CHROME, BrowserType.GOOGLECHROME) {
            @Override
            public WebDriver create(DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                return new ChromeDriver(desiredCapabilities);
            }
        },
        FIREFOX(BrowserType.FIREFOX) {
            @Override
            public WebDriver create(DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                return new FirefoxDriver(desiredCapabilities);
            }
        },
        IE(BrowserType.IE, BrowserType.IEXPLORE) {
            @Override
            public WebDriver create(DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                return new InternetExplorerDriver(desiredCapabilities);
            }
        },
        SAFARI(BrowserType.SAFARI) {
            @Override
            public WebDriver create(DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                return new SafariDriver(desiredCapabilities);
            }
        },
        PHANTOMJS(BrowserType.PHANTOMJS) {
            @Override
            public WebDriver create(DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                return new PhantomJSDriver(desiredCapabilities);
            }
        };

        private Set<String> types;

        private WebDriverType(String ... types) {
            this.types = Sets.newHashSet();
            for (String type : types) {
                this.types.add(type.toLowerCase());
            }
        }

        public abstract WebDriver create(DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities);

        public static WebDriverType typeFor(String value) {
            value = value.toLowerCase();
            for (WebDriverType type : WebDriverType.values()) {
                if (type.types.contains(value)) return type;
            }
            throw new IllegalArgumentException(format("Type %s is not valid", value));
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "minium.webdriver")
    public WebDriverProperties webDriverProperties() {
        return new WebDriverProperties();
    }

    @Autowired
    @Bean(destroyMethod = "quit")
    @JsVariable("wd")
    public WebDriver wd(WebDriverProperties webDriverProperties, Environment env) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities(webDriverProperties.getDesiredCapabilities());
        DesiredCapabilities requiredCapabilities = new DesiredCapabilities(webDriverProperties.getRequiredCapabilities());
        WebDriver webDriver = null;
        if (webDriverProperties.getUrl() != null) {
            RemoteWebDriver remoteDriver = new RemoteWebDriver(webDriverProperties.getUrl(), desiredCapabilities, requiredCapabilities);
            remoteDriver.setFileDetector(new LocalFileDetector());
            webDriver = remoteDriver;
        } else {
            String browserName = desiredCapabilities == null ? null : desiredCapabilities.getBrowserName();
            if (Strings.isNullOrEmpty(browserName)) browserName = BrowserType.CHROME;
            webDriver = WebDriverType.typeFor(browserName).create(desiredCapabilities, requiredCapabilities);
        }
        WindowProperties window = webDriverProperties.getWindow();
        if (window != null) {
            DimensionProperties size = window.getSize();
            PointProperties position = window.getPosition();
            if (size != null) webDriver.manage().window().setSize(new Dimension(size.getWidth(), size.getHeight()));
            if (position != null) webDriver.manage().window().setPosition(new Point(position.getX(), position.getY()));
            if (window.isMaximized()) {
                webDriver.manage().window().maximize();
            }
        }
        return webDriver instanceof TakesScreenshot ? webDriver : new Augmenter().augment(webDriver);
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
    public Elements root(WebElementsFactory<DefaultWebElements> elementsFactory) {
        DefaultWebElements root = elementsFactory.createRoot();
        Minium.set(root);
        return root;
    }

    @Autowired
    @Bean
    public WebFinder<?> webFinder(List<WebModule> webModules) {
        return DefaultWebElements.by;
    }
}
