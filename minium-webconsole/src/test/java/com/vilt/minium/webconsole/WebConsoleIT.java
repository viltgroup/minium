/*
 * Copyright (C) 2013 The Minium Authors
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
package com.vilt.minium.webconsole;

import static com.vilt.minium.Minium.$;
import static com.vilt.minium.actions.Interactions.checkEmpty;
import static com.vilt.minium.actions.Interactions.checkNotEmpty;
import static com.vilt.minium.actions.Interactions.click;
import static com.vilt.minium.actions.Interactions.fill;
import static com.vilt.minium.actions.Interactions.get;
import static com.vilt.minium.actions.Interactions.waitWhileNotEmpty;
import static com.vilt.minium.actions.Interactions.withWaitingPreset;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vilt.minium.DefaultWebElements;
import com.vilt.minium.DefaultWebElementsDriver;

@ContextConfiguration(classes = TestConfiguration.class)
public class WebConsoleIT extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("remoteWebDriverUrl")
    private URL remoteWebDriverUrl;

    protected DefaultWebElementsDriver wd;

    @BeforeClass
    public void before() throws IOException {
        wd = new DefaultWebElementsDriver(createNativeWebDriver());
        wd.manage().window().setSize(new Dimension(1024, 768));

        wd.configure().waitingPreset("slow").timeout(20, TimeUnit.SECONDS).interval(1, TimeUnit.SECONDS);

        get(wd, "http://localhost:8080/minium-webconsole");
    }

    @AfterClass
    public void after() {
        wd.quit();
    }

    @BeforeMethod
    public void goToHomePage() throws IOException {
        get(wd, "http://localhost:8080/minium-webconsole");
    }

    @Test
    public void testCreatePhantomDriver() {
        // when
        click($(wd, ".dropdown-toggle").withText("Web Drivers"));

        click($(wd, "#browsers a").containingText("PhantomJS"));
        fill($(wd, "#webdriver-varname"), "phantomwd");

        click($(wd, "#webdriver-create-dialog .btn").withText("Create"));

        withWaitingPreset("slow").waitWhileNotEmpty($(wd, ".progress-bar"));

        click($(wd, ".dropdown-toggle").withText("Web Drivers"));
        click($(wd, "#browsers a").withText("List..."));

        DefaultWebElements varCol = $(wd, "#webdriver-list-dialog th").withText("Variable");
        DefaultWebElements webDrivers = $(wd, "#webdriver-list-dialog td").below(varCol);

        // then
        assertTrue(checkNotEmpty(webDrivers.withText("phantomwd")), "Could not find phantomwd web driver in the list");
    }
    
    @Test(dependsOnMethods = "testCreatePhantomDriver")
    public void testExecuteScript() {
        
    }

    @Test(dependsOnMethods = "testExecuteScript")
    public void testSelectorGadget() {
        
    }

    @Test(dependsOnMethods = "testSelectorGadget")
    public void testRemovePhantomDriver() {
        // given
        // testCreatePhantomDriver

        // when
        DefaultWebElements webDriversDropdown = $(wd, ".dropdown-toggle").withText("Web Drivers");
        DefaultWebElements webDriversListOption = $(wd, "#browsers a").withText("List...");
        DefaultWebElements varCol = $(wd, "#webdriver-list-dialog th").withText("Variable");
        DefaultWebElements webDriver = $(wd, "#webdriver-list-dialog td").below(varCol).withText("phantomwd");
        DefaultWebElements webDriverRemoveBtn = $(wd, "#webdriver-list-dialog .btn-danger").has(".fontello-cancel").rightOf(webDriver);
        DefaultWebElements progressBar = $(wd, ".progress-bar");
        DefaultWebElements successAlertMsg = $(wd, ".alert-success");
        
        click(webDriversDropdown);
        click(webDriversListOption);
        click(webDriverRemoveBtn);
        
        withWaitingPreset("slow").waitWhileNotEmpty(progressBar);
        waitWhileNotEmpty(successAlertMsg.containingText("removed!"));

        click(webDriversDropdown);
        click(webDriversListOption);
        
        // then
        assertTrue(checkEmpty(webDriver));
    }

    protected WebDriver createNativeWebDriver() {
        return new RemoteWebDriver(remoteWebDriverUrl, new DesiredCapabilities());
    }
}
