package minium.web.internal.drivers;

import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

public class MockWebElement implements WebElement, Locatable {

    private Point point = new Point(64, 96);

    @Override
    public Coordinates getCoordinates() {
        return new Coordinates() {

            @Override
            public Point onScreen() {
                return point;
            }

            @Override
            public Point onPage() {
                return point;
            }

            @Override
            public Point inViewPort() {
                return point;
            }

            @Override
            public Object getAuxiliary() {
                return MockWebElement.this;
            }
        };
    }

    @Override
    public void click() {
    }

    @Override
    public void submit() {
    }

    @Override
    public void sendKeys(CharSequence... keysToSend) {
    }

    @Override
    public void clear() {
    }

    @Override
    public String getTagName() {
        return "div";
    }

    @Override
    public String getAttribute(String name) {
        return null;
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public List<WebElement> findElements(By by) {
        return Collections.emptyList();
    }

    @Override
    public WebElement findElement(By by) {
        return null;
    }

    @Override
    public boolean isDisplayed() {
        return true;
    }

    @Override
    public Point getLocation() {
        return point;
    }

    @Override
    public Dimension getSize() {
        return new Dimension(320, 240);
    }

    @Override
    public String getCssValue(String propertyName) {
        return null;
    }

}
