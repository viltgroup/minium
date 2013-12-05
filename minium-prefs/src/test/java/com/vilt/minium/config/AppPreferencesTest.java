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
package com.vilt.minium.config;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.prefs.WebConsolePreferences;

public class AppPreferencesTest {

    private AppPreferences preferences;

    @BeforeMethod
    public void setUp() throws IOException {
        InputStream stream = AppPreferences.class.getClassLoader().getResourceAsStream("minium-test-config.json");
        preferences = new AppPreferences(new InputStreamReader(stream));
    }

    @Test
    public void testWebConsolePreferences() {
        WebConsolePreferences wcPrefs = preferences.get("webconsole", WebConsolePreferences.class);

        assertEquals(wcPrefs.getHost(), "testhost");
        assertEquals(wcPrefs.getPort(), 12345);
        assertEquals(wcPrefs.getShutdownPort(), 54321);
        assertEquals(wcPrefs.isEnableFileMenu(), false);
        assertEquals(wcPrefs.getChromeBin(), new File("c:/foo/chrome.exe"));
    }

    @Test
    public void testWebConsolePreferencesEmptyFile() throws IOException {
        InputStream stream = AppPreferences.class.getClassLoader().getResourceAsStream("minium-test-config-empty.json");
        AppPreferences emptyPreferences = new AppPreferences(new InputStreamReader(stream));

        WebConsolePreferences wcPrefs = emptyPreferences.get("webconsole", WebConsolePreferences.class, new WebConsolePreferences());

        assertEquals(wcPrefs.getHost(), "127.0.0.1");
        assertEquals(wcPrefs.getPort(), 18129);
        assertEquals(wcPrefs.getShutdownPort(), 18130);
        assertEquals(wcPrefs.isEnableFileMenu(), true);
    }
}
