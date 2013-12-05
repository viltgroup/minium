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
package com.vilt.minium.webconsole.controller;

import static com.vilt.minium.Minium.$;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.CREATED;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.actions.DebugInteractions;
import com.vilt.minium.script.WebElementsDriverFactory;
import com.vilt.minium.webconsole.webdrivers.WebDriverInfo;
import com.vilt.minium.webconsole.webdrivers.WebDriverInfo.Type;
import com.vilt.minium.webconsole.webdrivers.WebElementsDriverManager;

@Controller
@RequestMapping("/webDrivers")
public class WebDriversController {

    @Autowired private WebElementsDriverManager wdManager;
    @Autowired private WebElementsDriverFactory factory;

    @RequestMapping(value = "/{var}/create")
    @ResponseBody
    @ResponseStatus(CREATED)
    public void create(@PathVariable String var, @RequestParam("type") Type type, @RequestParam(value = "remoteUrl", required = false) String remoteUrl) {
        if (wdManager.contains(var)) throw new IllegalStateException(format("Variable %s already exists", var));

        WebElementsDriver<?> wd;
        if (StringUtils.isEmpty(remoteUrl)) {
            wd = createLocalWebDriver(type);
        } else {
            wd = createRemoteWebDriver(remoteUrl, type);
        }

        wdManager.put(var, type, wd);
    }

    private WebElementsDriver<?> createRemoteWebDriver(String remoteUrl, Type type) {
        DesiredCapabilities capabilities;
        switch (type) {
        case Chrome:
            capabilities = DesiredCapabilities.chrome();
            break;
        case Firefox:
            capabilities = DesiredCapabilities.firefox();
            break;
        case InternetExplorer:
            capabilities = DesiredCapabilities.internetExplorer();
            break;
        case Opera:
            capabilities = DesiredCapabilities.opera();
            break;
        case Safari:
            capabilities = DesiredCapabilities.safari();
            break;
        case PhantomJS:
            capabilities = DesiredCapabilities.phantomjs();
            break;
        default:
            throw new IllegalArgumentException("Invalid type");
        }

        return factory.remoteDriver(remoteUrl, capabilities);
    }

    private WebElementsDriver<?> createLocalWebDriver(Type type) {
        switch (type) {
        case Chrome:
            return factory.chromeDriver();
        case Firefox:
            return factory.firefoxDriver();
        case InternetExplorer:
            return factory.internetExplorerDriver();
        case Safari:
            return factory.safariDriver();
        case PhantomJS:
            return factory.ghostDriver();
        case Opera:
            throw new UnsupportedOperationException("Not implemented yet");
        default:
            throw new IllegalArgumentException("Invalid type");
        }
    }

    @RequestMapping(value = "")
    @ResponseBody
    public List<WebDriverInfo> getWebDriverVariables() {
        return wdManager.getWebDriverVariables();
    }

    @RequestMapping(value = "/{var}/quit")
    public ResponseEntity<Void> quit(@PathVariable("var") String var) {
        if (wdManager.delete(var)) {
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{var}/screenshot")
    public ResponseEntity<byte[]> takeWindowScreenshot(@PathVariable("var") String var) {
        WebElementsDriver<?> wd = wdManager.get(var);
        if (wd == null) return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DebugInteractions.takeWindowScreenshot($(wd), out);

        // disable cache
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        headers.setPragma("no-cache");
        headers.setExpires(0);
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.OK);
    }
}
