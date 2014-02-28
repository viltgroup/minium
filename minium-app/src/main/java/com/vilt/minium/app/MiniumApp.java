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

import static java.lang.String.format;
import static org.apache.commons.io.IOUtils.copy;
import static org.apache.commons.lang3.StringUtils.center;

import java.io.IOException;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.vilt.minium.app.EmbeddedBrowser.Listener;
import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.prefs.WebConsolePreferences;

public class MiniumApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniumApp.class);

    public static void main(String[] args) throws Exception {
        run(args);
    }

    public static ConfigurableApplicationContext run(String ... args) throws IOException {
        final ConfigurableApplicationContext context = launchService(args);
        launchBrowser(context);
        return context;
    }

    protected static void launchBrowser(final ConfigurableApplicationContext context) throws IOException {
        final EmbeddedBrowser browser = context.getBean(EmbeddedBrowser.class);
        browser.addListener(new Listener() {

            @Override
            public void closed() {
                SpringApplication.exit(context);
            }
        });
        browser.start();
    }

    public static ConfigurableApplicationContext launchService(String... args) throws IOException {
        printBanner();

        final ConfigurableApplicationContext context = new SpringApplicationBuilder(MiniumAppWebConfig.class)
            .headless(false)
            .showBanner(false)
            .run(args);

        final AppPreferences appPreferences = context.getBean(AppPreferences.class);
        final WebConsolePreferences webConsolePreferences = WebConsolePreferences.from(appPreferences);

        webConsolePreferences.validate();
        return context;
    }

    protected static void printBanner() throws IOException {
        StringWriter out = new StringWriter();
        out.write('\n');
        copy(MiniumApp.class.getResourceAsStream("minium-asciiart.txt"), out);
        out.write('\n');
        String version = MiniumApp.class.getPackage().getImplementationVersion();
        if (version != null) {
            out.write(center(format("(v%s)", version), 40));
            out.write('\n');
        }
        LOGGER.info(out.toString());
    }
}
