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
package com.vilt.minium.prefs;

import static com.google.common.base.Preconditions.checkState;
import static java.lang.String.format;

import java.io.File;

import org.apache.commons.lang3.SystemUtils;

public class WebConsolePreferences extends BasePreferences {

    private String host = "127.0.0.1";
    private int port = 18129;
    private int shutdownPort = 18130;
    private boolean enableFileMenu = true;
    private File chromeBin;

    public static WebConsolePreferences from(AppPreferences preferences) {
        return preferences == null ? new WebConsolePreferences() : preferences.get("webconsole", WebConsolePreferences.class);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getShutdownPort() {
        return shutdownPort;
    }

    public void setShutdownPort(int shutdownPort) {
        this.shutdownPort = shutdownPort;
    }

    public boolean isEnableFileMenu() {
        return enableFileMenu;
    }

    public void setEnableFileMenu(boolean enableFileMenu) {
        this.enableFileMenu = enableFileMenu;
    }

    public File getChromeBin() {
        if (chromeBin == null) {
            // Based on expected Chrome default locations:
            // https://code.google.com/p/selenium/wiki/ChromeDriver
            if (SystemUtils.IS_OS_WINDOWS_XP) {
                return oneOf(
                        new File(System.getenv("HOMEPATH"), "Local Settings\\Application Data\\Google\\Chrome\\Application\\chrome.exe"),
                        new File(System.getenv("PROGRAMFILES"), "Google\\Chrome\\Application\\chrome.exe"));
            } else if (SystemUtils.IS_OS_WINDOWS) {
                return oneOf(
                        new File(format("C:\\Users\\%s\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe", System.getenv("USERNAME"))),
                        new File(System.getenv("PROGRAMFILES"), "Google\\Chrome\\Application\\chrome.exe"),
                        new File(System.getenv("ProgramFiles(x86)"), "Google\\Chrome\\Application\\chrome.exe"));
            } else if (SystemUtils.IS_OS_LINUX) {
                return new File("/usr/bin/google-chrome");
            } else if (SystemUtils.IS_OS_MAC_OSX) {
                return new File("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
            }
        }
        return chromeBin;
    }

    public void setChromeBin(File chromeBin) {
        this.chromeBin = chromeBin;
    }

    @Override
    public void validate() {
        super.validate();

        File chromeBin = getChromeBin();
        checkState(chromeBin != null && chromeBin.exists(), "Chrome binary path %s does not exist, please ensure you edit "
                + "minium-prefs.json and set webconsole.chromeBin to point to chrome binary", chromeBin);

        checkState(chromeBin.isFile(), "Chrome binary path %s is not a file", chromeBin);
        checkState(chromeBin.canExecute(), "Chrome binary path %s cannot execute", chromeBin);
    }

}
