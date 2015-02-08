package minium.web.internal.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import minium.Dimension;
import minium.Elements;
import minium.Point;
import minium.actions.internal.AbstractInteraction;
import minium.web.DocumentWebDriver;
import minium.web.actions.Browser;
import minium.web.actions.Browser.Cookie;
import minium.web.actions.Browser.Window;
import minium.web.actions.HasBrowser;
import minium.web.internal.InternalWebElements;

import org.openqa.selenium.OutputType;

import platypus.Mixin;

import com.google.common.io.Files;

public class DefaultHasBrowser extends Mixin.Impl implements HasBrowser {

    class DefaultNavigation implements Browser.Navigation {

        @Override
        public void back() {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().back();
                }
            }.perform();
        }

        @Override
        public void forward() {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().forward();
                }
            }.perform();
        }

        @Override
        public void to(final String url) {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().to(url);
                }
            }.perform();
        }

        @Override
        public void to(final URL url) {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().to(url);
                }
            }.perform();
        }

        @Override
        public void refresh() {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().refresh();
                }
            }.perform();
        }

    }

    class DefaultWindow implements Browser.Window {

        @Override
        public void setSize(final Dimension targetSize) {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    org.openqa.selenium.Dimension seleniumSize = new org.openqa.selenium.Dimension(targetSize.width(), targetSize.height());
                    documentDriver().manage().window().setSize(seleniumSize);
                }
            }.perform();
        }

        @Override
        public void setPosition(final Point targetPosition) {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    org.openqa.selenium.Point seleniumPoint = new org.openqa.selenium.Point(targetPosition.x(), targetPosition.y());
                    documentDriver().manage().window().setPosition(seleniumPoint);
                }
            }.perform();
        }

        @Override
        public Dimension getSize() {
            org.openqa.selenium.Dimension size = documentDriver().manage().window().getSize();
            return new Dimension(size.getWidth(), size.getHeight());
        }

        @Override
        public Point getPosition() {
            org.openqa.selenium.Point position = documentDriver().manage().window().getPosition();
            return new Point(position.getX(), position.getY());
        }

        @Override
        public void maximize() {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    documentDriver().manage().window().maximize();
                }
            }.perform();
        }

    }

    class DefaultOptions implements Browser.Options {

        @Override
        public void addCookie(Cookie cookie) {
            throw new UnsupportedOperationException("not implemented yet");
        }

        @Override
        public void deleteCookieNamed(String name) {
            throw new UnsupportedOperationException("not implemented yet");
        }

        @Override
        public void deleteCookie(Cookie cookie) {
            throw new UnsupportedOperationException("not implemented yet");
        }

        @Override
        public void deleteAllCookies() {
            throw new UnsupportedOperationException("not implemented yet");
        }

        @Override
        public Set<Cookie> getCookies() {
            throw new UnsupportedOperationException("not implemented yet");
        }

        @Override
        public Cookie getCookieNamed(String name) {
            throw new UnsupportedOperationException("not implemented yet");
        }

        @Override
        public Window window() {
            return new DefaultWindow();
        }

    }

    class DefaultScreenshot implements Browser.Screenshot {

        @Override
        public byte[] asBytes() {
            return documentDriver().getScreenshotAs(OutputType.BYTES);
        }

        @Override
        public File asFile() {
            return documentDriver().getScreenshotAs(OutputType.FILE);
        }

        @Override
        public void saveTo(File file) throws IOException {
            Files.asByteSource(file).copyTo(Files.asByteSink(file));
        }

    }

    class DefaultBrowser implements Browser {

        @Override
        public void get(final String url) {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    documentDriver().get(url);
                }
            }.perform();
        }

        @Override
        public String getCurrentUrl() {
            return documentDriver().getCurrentUrl();
        }

        @Override
        public String getTitle() {
            return documentDriver().getTitle();
        }

        @Override
        public void close() {
            new AbstractInteraction(DefaultHasBrowser.this.as(Elements.class)) {
                @Override
                protected void doPerform() {
                    documentDriver().close();
                }
            }.perform();
        }

        @Override
        public Navigation navigate() {
            return new DefaultNavigation();
        }

        @Override
        public Options manage() {
            return new DefaultOptions();
        }

        @Override
        public Screenshot screenshot() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    @Override
    public Browser browser() {
        return new DefaultBrowser();
    }

    protected DocumentWebDriver documentDriver() {
        return this.as(InternalWebElements.class).documentDriver();
    }
}
