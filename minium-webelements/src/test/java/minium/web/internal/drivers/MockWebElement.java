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
package minium.web.internal.drivers;

import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;

import com.google.common.base.Objects;

public class MockWebElement implements WebElement, Locatable {

    class MockCoordinates implements Coordinates {
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

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Coordinates) {
                Coordinates other = (Coordinates) obj;
                return Objects.equal(onScreen(), other.onScreen()) &&
                        Objects.equal(onPage(), other.onPage()) &&
                        Objects.equal(inViewPort(), other.inViewPort());
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(point);
        }
    }

    private Point point = new Point(64, 96);
    MockCoordinates mockCoordinates = new MockCoordinates();

    @Override
    public Coordinates getCoordinates() {
        return mockCoordinates;
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
