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
package minium.web.utils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import minium.web.utils.dto.MonitoringReportDTO;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceUtils.class);

    private PerformanceUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getPerformanceJson(WebDriver webdriver, String url) {
        MonitoringReportDTO monitoringReportDTO = new MonitoringReportDTO(url);

        MonitoringReportDTO.Performance performance =
                (MonitoringReportDTO.Performance) getValue(webdriver,
                        "return JSON.stringify(window.performance)",
                        MonitoringReportDTO.Performance.class);

        MonitoringReportDTO.Stats stats =
                (MonitoringReportDTO.Stats) getValue(webdriver,
                        "var numberOfRequests = 0; " +
                                "var pageSize = 0; " +
                                "performance.getEntriesByType('resource').forEach((r) => { numberOfRequests++; pageSize += r.transferSize }); " +
                                "return JSON.stringify({\"pageSize\": pageSize, \"numberOfRequests\": numberOfRequests })",
                        MonitoringReportDTO.Stats.class);

        monitoringReportDTO.setData(performance);
        monitoringReportDTO.setStats(stats);
        monitoringReportDTO.setStatusCode(processStatusCode(webdriver, url));
        monitoringReportDTO.setJsErrors(webdriver.manage().logs().get(LogType.BROWSER).filter(Level.SEVERE));

        String result = "";
        try {
            result = new ObjectMapper().writeValueAsString(monitoringReportDTO);
        } catch (JsonProcessingException e) {
            LOGGER.warn("Error converting the Object MonitoringReportDTO to String", e);
        }

        return result;
    }

    private static Object getValue(WebDriver webdriver, String expression, Class<?> clazz) {
        try {
            String result = (String) ((JavascriptExecutor) webdriver).executeScript(expression);
            return new ObjectMapper().readValue(result, clazz);
        } catch (IOException e) {
            LOGGER.warn("Error processing the performance data for the class {}", clazz.getName(), e);
        }

        return null;
    }

    private static int processStatusCode(WebDriver webdriver, String url) {
        int statusCode = -1;
        Pattern methodPattern = Pattern.compile("Network.responseReceived");
        Pattern urlPattern = Pattern.compile("\"url\":\"" + url + "/?\"");
        Pattern statusPattern = Pattern.compile("\"status\":(\\d+)");

        try {
            LogEntries logEntries = webdriver.manage().logs().get(LogType.PERFORMANCE);
            for (LogEntry logEntry : logEntries) {
                Matcher methodMatcher = methodPattern.matcher(logEntry.getMessage());
                Matcher urlMatcher = urlPattern.matcher(logEntry.getMessage());

                if (methodMatcher.find() && urlMatcher.find()) {
                    Matcher statusMatcher = statusPattern.matcher(logEntry.getMessage());
                    if (statusMatcher.find()) {
                        statusCode = Integer.parseInt(statusMatcher.group(1));
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.warn("Error processing the performance logs extracted from the browser", e);
        }

        if (statusCode == -1) {
            HttpClientBuilder builder = HttpClientBuilder.create();

            String httpProxy = System.getProperty("minium.monitoring.performance.httpProxy");
            if (!(httpProxy == null || httpProxy.isEmpty())) {
                String[] proxyConfigs = httpProxy.split(":");
                int proxyPort = Integer.parseInt(proxyConfigs[1]);
                builder.setProxy(new HttpHost(proxyConfigs[0], proxyPort));
            }

            HttpClient client = builder.build();
            HttpResponse response;
            try {
                response = client.execute(new HttpGet(url));
                statusCode = response.getStatusLine().getStatusCode();
            } catch (IOException e) {
                LOGGER.warn("Error at the http response to extract from the http response code", e);
            }
        }

        return statusCode;
    }
}
