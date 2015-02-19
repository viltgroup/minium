package minium.web;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.HasInputDevices;

public interface DocumentWebDriver extends WebDriver, JavascriptExecutor, HasInputDevices, TakesScreenshot {
    public boolean isClosed();
}