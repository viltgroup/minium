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
package com.vilt.minium.webconsole.webdrivers;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.vilt.minium.WebElementsDriver;
import com.vilt.minium.script.MiniumScriptEngine;
import com.vilt.minium.webconsole.webdrivers.WebDriverInfo.Type;

@Component
public class WebElementsDriverManager {

    @Autowired private MiniumScriptEngine engine;

    private Map<String, WebElementsDriver<?>> webDrivers = Maps.newHashMap();
    private Map<String, WebDriverInfo> webDriversInfo = Maps.newHashMap();

    public boolean contains(String var) {
        return webDrivers.get(var) != null;
    }

    public WebElementsDriver<?> get(String var) {
        return webDrivers.get(var);
    }

    public void put(String var, Type type, WebElementsDriver<?> driver) {
        put(new WebDriverInfo(var, type), driver);
    }

    public void put(WebDriverInfo wdInfo, WebElementsDriver<?> driver) {
        String variableName = wdInfo.getVarName();

        if (engine.contains(variableName)) {
            throw new IllegalStateException(format("Variable %s already exists in script engine", wdInfo.getVarName()));
        }
        webDrivers.put(variableName, driver);
        webDriversInfo.put(variableName, wdInfo);
        engine.put(variableName, driver);
    }

    public boolean delete(String var) {
        webDriversInfo.remove(var);
        WebElementsDriver<?> wd = webDrivers.remove(var);
        engine.delete(var);

        if (wd != null) {
            wd.quit();
            return true;
        }
        return false;
    }

    public List<WebDriverInfo> getWebDriverVariables() {
        return Lists.newArrayList(webDriversInfo.values());
    }

}
