package minium.web.config;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;

import minium.web.config.WebDriverProperties.DimensionProperties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebDriverConfigurationTest.TestConfig.class)
@ActiveProfiles("test")
public class WebDriverConfigurationTest {

    @Configuration
    @EnableConfigurationProperties
    public static class TestConfig {
        @Bean
        @ConfigurationProperties(prefix = "minium.webdriver")
        public WebDriverProperties webDriverProperties() {
            return new WebDriverProperties();
        }
    }

    @Autowired
    private WebDriverProperties webDriverProperties;

    @Test
    public void testWebElementsDriverProperties() throws MalformedURLException {
        DesiredCapabilities expectedDesiredCapabilities = new DesiredCapabilities(BrowserType.CHROME, "39.0", Platform.LINUX);
        DesiredCapabilities expectedRequiredCapabilities = new DesiredCapabilities(ImmutableMap.of(CapabilityType.PLATFORM, Platform.LINUX.name()));
        assertThat(new DesiredCapabilities(webDriverProperties.getDesiredCapabilities()), equalTo(expectedDesiredCapabilities));
        assertThat(new DesiredCapabilities(webDriverProperties.getRequiredCapabilities()), equalTo(expectedRequiredCapabilities));
        assertThat(webDriverProperties.getUrl(), equalTo(new URL("http://localhost:4444/wd/hub")));
        assertThat(webDriverProperties.getWindow().getSize(), equalTo(new DimensionProperties(1280, 1024)));
        assertThat(webDriverProperties.getWindow().getPosition(), nullValue());
    }
}
