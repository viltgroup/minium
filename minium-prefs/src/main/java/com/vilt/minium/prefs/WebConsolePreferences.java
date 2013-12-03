package com.vilt.minium.prefs;

import static java.lang.String.format;

import java.io.File;

import org.apache.commons.lang.SystemUtils;

public class WebConsolePreferences {

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
            }
            else if (SystemUtils.IS_OS_WINDOWS) {
                return oneOf(
                    new File(format("C:\\Users\\%s\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe", System.getenv("USERNAME"))),
                    new File(System.getenv("PROGRAMFILES"), "Google\\Chrome\\Application\\chrome.exe"));
            }
            else if (SystemUtils.IS_OS_LINUX) {
                return new File("/usr/bin/google-chrome");
            }
            else if (SystemUtils.IS_OS_MAC_OSX) {
                return new File("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
            }
        }
        return chromeBin;
    }
    
    public void setChromeBin(File chromeBin) {
        this.chromeBin = chromeBin;
    }
    
    private File oneOf(File ... files) {
        for (File file : files) {
            if (file.exists()) return file;
        }
        
        return null;
    }
}
