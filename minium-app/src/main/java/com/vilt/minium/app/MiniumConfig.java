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
package com.vilt.minium.app;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.context.annotation.Bean;

import com.vilt.minium.WebElements;
import com.vilt.minium.app.webelements.SelectorGadgetWebElements;
import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.script.MiniumScriptEngine;
import com.vilt.minium.script.WebElementsDriverFactory;
import com.vilt.minium.tips.TipWebElements;

public class MiniumConfig {

    static {
        File baseDir = System.getProperty("minium.home") != null ?
            new File(System.getProperty("minium.home")) :
            SystemUtils.getJavaIoTmpDir();
        File logFile = new File(baseDir, "minium-app.log");
        System.setProperty("LOG_FILE", logFile.getAbsolutePath());
    }

    @SuppressWarnings("unchecked")
    private static final Class<? extends WebElements>[] WEB_ELEMS_INTFS = (Class<? extends WebElements>[]) new Class<?>[] {
        DebugWebElements.class,
        TipWebElements.class,
        SelectorGadgetWebElements.class
    };

    @Bean
    public AppPreferences appPreferences() throws IOException {
        return new AppPreferences();
    }

    @Bean(destroyMethod = "destroy")
    public WebElementsDriverFactory webElementsDriverFactory() throws IOException {
        WebElementsDriverFactory webElementsDriverFactory = new WebElementsDriverFactory(WEB_ELEMS_INTFS);
        webElementsDriverFactory.setPreferences(appPreferences());
        return webElementsDriverFactory;
    }

    @Bean
    public MiniumScriptEngine scriptEngine() throws IOException {
        MiniumScriptEngine scriptEngine = new MiniumScriptEngine(webElementsDriverFactory(), appPreferences());
        return scriptEngine;
    }

}
