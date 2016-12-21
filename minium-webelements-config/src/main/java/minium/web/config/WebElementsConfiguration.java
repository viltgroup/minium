/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.web.config;

import static minium.web.internal.WebModules.combine;
import static minium.web.internal.WebModules.debugModule;
import static minium.web.internal.WebModules.defaultModule;

import java.io.IOException;
import java.util.List;

import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.actions.Browser;
import minium.web.actions.WebDriverBrowser;
import minium.web.config.services.DriverServicesProperties;
import minium.web.internal.WebModule;
import minium.web.internal.WebModules;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
    @Lazy
    public WebDriver wd(WebDriverFactory webDriverFactory, WebDriverProperties webDriverProperties) throws IOException {
        return webDriverFactory.create(webDriverProperties);
    }

    @Autowired
    @Bean
    @Lazy
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebModule defaultWebModule(WebDriver wd) {
        return combine(defaultModule(wd), debugModule());
    }

    @Autowired
    @Bean(destroyMethod = "quit")
    @Lazy
    public Browser<DefaultWebElements> browser(WebDriver webDriver, List<WebModule> modules) {
        WebModule combinedWebModule = WebModules.combine(modules);
        return new WebDriverBrowser<DefaultWebElements>(webDriver, DefaultWebElements.class, combinedWebModule);
    }
}
