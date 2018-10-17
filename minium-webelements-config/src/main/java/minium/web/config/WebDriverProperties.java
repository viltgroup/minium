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
package minium.web.config;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

public class WebDriverProperties {

    public static class DimensionProperties {
        private int width;
        private int height;

        public DimensionProperties() {
        }

        public DimensionProperties(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DimensionProperties) {
                DimensionProperties other = (DimensionProperties) obj;
                return Objects.equal(this.width, other.width) &&
                        Objects.equal(this.height, other.height);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(width, height);
        }
    }

    public static class PointProperties {
        private int x;
        private int y;

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof PointProperties) {
                PointProperties other = (PointProperties) obj;
                return Objects.equal(this.x, other.x) &&
                        Objects.equal(this.y, other.y);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(x, y);
        }
    }

    public static class WindowProperties {
        private DimensionProperties size;
        private PointProperties position;
        private boolean maximized/* = false */;

        public DimensionProperties getSize() {
            return size;
        }

        public void setSize(DimensionProperties size) {
            this.size = size;
        }

        public PointProperties getPosition() {
            return position;
        }

        public void setPosition(PointProperties position) {
            this.position = position;
        }

        public boolean isMaximized() {
            return maximized;
        }

        public void setMaximized(boolean maximized) {
            this.maximized = maximized;
        }
    }

    public enum PreferenceType {
        BOOLEAN, INTEGER, STRING
    };

    public static class PreferenceProperties {

        private String name;
        private PreferenceType type;
        private Object value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PreferenceType getType() {
            return type;
        }

        public void setType(PreferenceType type) {
            this.type = type;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

    public static class FirefoxProfileProperties {

        private String dir;
        private List<File> extensions;
        private List<PreferenceProperties> preferences;
        private boolean loadNoFocusLib;
        private boolean acceptUntrustedCerts;
        private boolean untrustedCertIssuer;

        public FirefoxProfileProperties() {
        }

        public List<File> getExtensions() {
            return extensions;
        }

        public void setExtensions(List<File> extensions) {
            this.extensions = extensions;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String profileDir) {
            this.dir = profileDir;
        }

        public List<PreferenceProperties> getPreferences() {
            return preferences;
        }

        public void setPreferences(List<PreferenceProperties> preferences) {
            this.preferences = preferences;
        }

        public boolean shouldLoadNoFocusLib() {
            return loadNoFocusLib;
        }

        public void setLoadNoFocusLib(boolean loadNoFocusLib) {
            this.loadNoFocusLib = loadNoFocusLib;
        }

        public boolean shouldAcceptUntrustedCerts() {
            return acceptUntrustedCerts;
        }

        public void setAcceptUntrustedCerts(boolean acceptUntrustedCerts) {
            this.acceptUntrustedCerts = acceptUntrustedCerts;
        }

        public boolean shouldUntrustedCertIssuer() {
            return untrustedCertIssuer;
        }

        public void setUntrustedCertIssuer(boolean untrustedCertIssuer) {
            this.untrustedCertIssuer = untrustedCertIssuer;
        }
    }

    public static class ChromeOptionsProperties {
        private List<String> args;
        private File binary;
        private List<File> extensions;
        private Map<String, Object> preferences;

        public ChromeOptionsProperties() {
        }

        public List<String> getArgs() {
            return args;
        }

        public void setArgs(List<String> args) {
            this.args = args;
        }

        public File getBinary() {
            return binary;
        }

        public void setBinary(File binary) {
            this.binary = binary;
        }

        public List<File> getExtensions() {
            return extensions;
        }

        public void setExtensions(List<File> extensions) {
            this.extensions = extensions;
        }

        public Map<String, Object> getPreferences() {
            return preferences;
        }

        public void setPreferences(Map<String, Object> preferences) {
            this.preferences = preferences;
        }
    }

    private Map<String, Object> desiredCapabilities = Maps.newHashMap();
    private URL url;
    private WindowProperties window;
    private boolean stateful = true;
    private FirefoxProfileProperties firefoxProfile;
    private ChromeOptionsProperties chromeOptions;

    public WebDriverProperties() {
        desiredCapabilities.put(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
    }

    public Map<String, Object> getDesiredCapabilities() {
        return desiredCapabilities;
    }

    public void setDesiredCapabilities(Map<String, Object> capabilities) {
        this.desiredCapabilities = capabilities;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public WindowProperties getWindow() {
        return window;
    }

    public void setWindow(WindowProperties window) {
        this.window = window;
    }

    public boolean isStateful() {
        return stateful;
    }

    public void setStateful(boolean stateful) {
        this.stateful = stateful;
    }

    public FirefoxProfileProperties getFirefoxProfile() {
        return firefoxProfile;
    }

    public void setFirefoxProfile(FirefoxProfileProperties firefoxProfile) {
        this.firefoxProfile = firefoxProfile;
    }

    public ChromeOptionsProperties getChromeOptions() {
        return chromeOptions;
    }

    public void setChromeOptions(ChromeOptionsProperties chromeOptions) {
        this.chromeOptions = chromeOptions;
    }
}
