package minium.web.internal.drivers;

import minium.web.DocumentWebDriver;

import org.openqa.selenium.WebDriver;

public interface InternalDocumentWebDriver extends DocumentWebDriver {
    public abstract void ensureSwitch();
    public WebDriver nativeWebDriver();
}
