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

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.vilt.minium.WebElements;
import com.vilt.minium.app.EmbeddedBrowser.Listener;
import com.vilt.minium.app.json.ConsoleObjectMapper;
import com.vilt.minium.app.webelements.SelectorGadgetWebElements;
import com.vilt.minium.debug.DebugWebElements;
import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.prefs.WebConsolePreferences;
import com.vilt.minium.script.MiniumScriptEngine;
import com.vilt.minium.script.WebElementsDriverFactory;
import com.vilt.minium.tips.TipWebElements;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class MiniumApp {

    static {
        File baseDir = System.getProperty("minium.home") != null ?
            new File(System.getProperty("minium.home")) :
            SystemUtils.getJavaIoTmpDir();
        File logFile = new File(baseDir, "minium-app.log");
        System.setProperty("LOG_FILE", logFile.getAbsolutePath());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MiniumApp.class);

    @SuppressWarnings("unchecked")
    private static final Class<? extends WebElements>[] WEB_ELEMS_INTFS = (Class<? extends WebElements>[]) new Class<?>[] {
        DebugWebElements.class,
        TipWebElements.class,
        SelectorGadgetWebElements.class
    };

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding(Charsets.UTF_8.name());
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayConverter() {
        ByteArrayHttpMessageConverter converter = new ByteArrayHttpMessageConverter();
        converter.setSupportedMediaTypes(Lists.newArrayList(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG, MediaType.IMAGE_GIF));
        return converter;
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJacksonConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(new ConsoleObjectMapper());
        return converter;
    }

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
        MiniumScriptEngine scriptEngine = new MiniumScriptEngine(webElementsDriverFactory());
        scriptEngine.setPreferences(appPreferences());
        return scriptEngine;
    }

    @Bean
    public EmbeddedBrowser embeddedBrowser() throws IOException {
        EmbeddedBrowser browser = new EmbeddedBrowser(appPreferences().getBaseDir(), WebConsolePreferences.from(appPreferences()));
        return browser;
    }

    @Bean
    public TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory() throws IOException {
        WebConsolePreferences prefs = WebConsolePreferences.from(appPreferences());
        return new TomcatEmbeddedServletContainerFactory(prefs.getPort());
    }

    public static void main(String[] args) throws Exception {
        run(args);
    }

    public static ConfigurableApplicationContext run(String ... args) throws IOException {
        printBanner();

        final ConfigurableApplicationContext context = new SpringApplicationBuilder(MiniumApp.class)
            .showBanner(false)
            .run(args);

        final AppPreferences appPreferences = context.getBean(AppPreferences.class);
        final WebConsolePreferences webConsolePreferences = WebConsolePreferences.from(appPreferences);

        webConsolePreferences.validate();

        final EmbeddedBrowser browser = context.getBean(EmbeddedBrowser.class);
        browser.addListener(new Listener() {

            @Override
            public void closed() {
                SpringApplication.exit(context);
            }
        });
        browser.start();

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
