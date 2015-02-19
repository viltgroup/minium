package minium.web.config;

import static java.lang.String.format;

import java.util.Set;

import minium.web.config.WebDriverProperties.DimensionProperties;
import minium.web.config.WebDriverProperties.PointProperties;
import minium.web.config.WebDriverProperties.WindowProperties;
import minium.web.config.services.ChromeDriverServiceProperties;
import minium.web.config.services.DriverServicesProperties;
import minium.web.config.services.InternetExplorerDriverServiceProperties;
import minium.web.config.services.PhantomJsDriverServiceProperties;

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
import org.openqa.selenium.remote.service.DriverService;
import org.openqa.selenium.safari.SafariDriver;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public class WebDriverFactory {

    private final DriverServicesProperties driverServices;

    enum WebDriverType {
        CHROME(BrowserType.CHROME, BrowserType.GOOGLECHROME) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                ChromeDriverServiceProperties serviceProperties = webDriverFactory.driverServices == null ? null : webDriverFactory.driverServices.getChrome();
                DriverService driverService = serviceProperties == null ? null : serviceProperties.getDriverService();
                return driverService == null ? new ChromeDriver(desiredCapabilities) : new RemoteWebDriver(driverService.getUrl(), desiredCapabilities, requiredCapabilities);
            }
        },
        FIREFOX(BrowserType.FIREFOX) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                return new FirefoxDriver(desiredCapabilities);
            }
        },
        IE(BrowserType.IE, BrowserType.IEXPLORE) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                InternetExplorerDriverServiceProperties serviceProperties = webDriverFactory.driverServices == null ? null : webDriverFactory.driverServices.getInternetExplorer();
                DriverService driverService = serviceProperties == null ? null : serviceProperties.getDriverService();
                return driverService == null ? new InternetExplorerDriver(desiredCapabilities) : new RemoteWebDriver(driverService.getUrl(), desiredCapabilities, requiredCapabilities);
            }
        },
        SAFARI(BrowserType.SAFARI) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                return new SafariDriver(desiredCapabilities);
            }
        },
        PHANTOMJS(BrowserType.PHANTOMJS) {
            @Override
            public WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities) {
                PhantomJsDriverServiceProperties serviceProperties = webDriverFactory.driverServices == null ? null : webDriverFactory.driverServices.getPhantomJs();
                DriverService driverService = serviceProperties == null ? null : serviceProperties.getDriverService();
                return driverService == null ? new PhantomJSDriver(desiredCapabilities) : new RemoteWebDriver(driverService.getUrl(), desiredCapabilities, requiredCapabilities);
            }
        };

        private Set<String> types;

        private WebDriverType(String ... types) {
            this.types = Sets.newHashSet();
            for (String type : types) {
                this.types.add(type.toLowerCase());
            }
        }

        public abstract WebDriver create(WebDriverFactory webDriverFactory, DesiredCapabilities desiredCapabilities, DesiredCapabilities requiredCapabilities);

        public static WebDriverType typeFor(String value) {
            value = value.toLowerCase();
            for (WebDriverType type : WebDriverType.values()) {
                if (type.types.contains(value)) return type;
            }
            throw new IllegalArgumentException(format("Type %s is not valid", value));
        }
    }

    public WebDriverFactory(DriverServicesProperties driverServices) {
        this.driverServices = driverServices;
    }

    public WebDriver create(WebDriverProperties webDriverProperties) {
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
            webDriver = WebDriverType.typeFor(browserName).create(this, desiredCapabilities, requiredCapabilities);
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
}
