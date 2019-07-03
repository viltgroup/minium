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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PerformanceUtils {

    public static String getPerformanceJson(WebDriver webdriver, String url) {
        final ObjectMapper mapper = new ObjectMapper();

        Map<?, ?> performance = (Map<?, ?>) ((JavascriptExecutor) webdriver).executeScript("return window.performance");
        String performanceJson = null;

        List<LogEntry> jsErrors = webdriver.manage().logs().get(LogType.BROWSER).filter(Level.SEVERE);
        String jsErrorsJson = null;

        Map<?, ?> stats = (Map<?, ?>) ((JavascriptExecutor) webdriver).executeScript(
                "var numberOfRequests = 0;var pageSize = 0; performance.getEntriesByType('resource').forEach((r) => { numberOfRequests++; pageSize += r.transferSize }); return {pageSize, numberOfRequests}");
        String statsJson = null;

        try {
            performanceJson = mapper.writeValueAsString(performance);
            jsErrorsJson = mapper.writeValueAsString(jsErrors);
            statsJson = mapper.writeValueAsString(stats);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
            e.printStackTrace();
        }

        if (statusCode == -1) {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response;
            try {
                response = client.execute(new HttpGet(url));
                statusCode = response.getStatusLine().getStatusCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "{ \"url\": \"" + url + "\", \"data\": " + performanceJson + ", \"stats\": " + statsJson + ", \"statusCode\": " + statusCode + ", \"jsErrors\": "
                + jsErrorsJson + " }";
    }
}
