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
package minium.cucumber;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import cucumber.api.Scenario;
import minium.actions.internal.AfterInteractionEvent;
import minium.actions.internal.DefaultInteractionListener;
import minium.web.EvalWebElements;
import minium.web.internal.HasNativeWebDriver;
import minium.web.internal.actions.GetInteraction;

public class GetInteractionListener extends DefaultInteractionListener {
    private Scenario scenario;

    public GetInteractionListener(Scenario s) {
        this.scenario = s;
    }
    @Override
    protected void onAfterEvent(AfterInteractionEvent event) {
        if (event.getInteraction() instanceof GetInteraction) {
            GetInteraction interaction = (GetInteraction) event.getInteraction();
            String performance = (String) event.getSource().as(EvalWebElements.class).eval("return window.performance");
            WebDriver webdriver = (WebDriver) event.getSource().as(HasNativeWebDriver.class).nativeWebDriver();

            List<LogEntry> jsErrors = webdriver.manage().logs().get(LogType.BROWSER).filter(Level.SEVERE);
            final ObjectMapper mapper = new ObjectMapper();
            String jsErrorsJson = null;
            try {
                jsErrorsJson = mapper.writeValueAsString(jsErrors);
            } catch (JsonProcessingException e) {
            }

            int statusCode = -1;
            try {
                LogEntries les = webdriver.manage().logs().get(LogType.PERFORMANCE);
                for (LogEntry le : les) {

                    String method = "Network.responseReceived";
                    Pattern methodPattern = Pattern.compile(method);
                    Matcher methodMatcher = methodPattern.matcher(le.getMessage());

                    String url = "\"url\":\"" + interaction.getUrl() + "/?\"";
                    Pattern urlPattern = Pattern.compile(url);
                    Matcher urlMatcher = urlPattern.matcher(le.getMessage());
                    if (methodMatcher.find() && urlMatcher.find()) {
                        String status = "\"status\":(\\d+)";
                        Pattern statusPattern = Pattern.compile(status);
                        Matcher statusMatcher = statusPattern.matcher(le.getMessage());
                        if (statusMatcher.find()) {
                            System.out.println(le.getMessage());
                            statusCode = Integer.parseInt(statusMatcher.group(1));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (statusCode == -1) {
                HttpClient client = HttpClientBuilder.create().build();
                HttpResponse response;
                try {
                    response = client.execute(new HttpGet(interaction.getUrl()));
                    statusCode = response.getStatusLine().getStatusCode();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            String stats = (String) event.getSource().as(EvalWebElements.class).eval("var numberOfRequests = 0;var pageSize = 0; performance.getEntriesByType('resource').forEach((r) => { numberOfRequests++; pageSize += r.transferSize }); return {pageSize, numberOfRequests}");
            String output = "{ \"url\": \"" + interaction.getUrl() + "\", \"data\": " + performance + ", \"stats\": " + stats + ", \"statusCode\": " + statusCode + ", \"jsErrors\": " + jsErrorsJson + " }";
            scenario.write(output);
        }
    }
}