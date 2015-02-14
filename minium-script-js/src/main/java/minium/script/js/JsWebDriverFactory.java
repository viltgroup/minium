package minium.script.js;

import org.openqa.selenium.WebDriver;

public interface JsWebDriverFactory {

    public abstract WebDriver create(Object obj);

}