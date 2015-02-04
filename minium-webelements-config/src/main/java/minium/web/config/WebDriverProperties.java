package minium.web.config;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
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
        private boolean maximized = false;

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

    private List<Class<?>> elementInterfaces = Lists.newArrayList();
    private Map<String, Object> desiredCapabilities = Maps.newHashMap();
    private Map<String, Object> requiredCapabilities = Maps.newHashMap();
    private URL url;
    private WindowProperties window;

    public WebDriverProperties() {
        desiredCapabilities.put(CapabilityType.BROWSER_NAME, BrowserType.CHROME);
    }

    public List<Class<?>> getElementInterfaces() {
        return elementInterfaces;
    }

    public void setElementInterfaces(List<Class<?>> elementInterfaces) {
        this.elementInterfaces = elementInterfaces;
    }

    public Map<String, Object> getDesiredCapabilities() {
        return desiredCapabilities;
    }

    public void setDesiredCapabilities(Map<String, Object> capabilities) {
        this.desiredCapabilities = capabilities;
    }

    public Map<String, Object> getRequiredCapabilities() {
        return requiredCapabilities;
    }

    public void setRequiredCapabilities(Map<String, Object> requiredCapabilities) {
        this.requiredCapabilities = requiredCapabilities;
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

}
