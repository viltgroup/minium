package minium.web.internal;

import org.openqa.selenium.WebDriver;

public interface HasNativeWebDriver {

    public WebDriver nativeWebDriver();

    public static class Impl implements HasNativeWebDriver {

        private final WebDriver nativeWebDriver;

        public Impl(WebDriver nativeWebDriver) {
            this.nativeWebDriver = nativeWebDriver;
        }

        @Override
        public WebDriver nativeWebDriver() {
            return nativeWebDriver;
        }

    }
}
