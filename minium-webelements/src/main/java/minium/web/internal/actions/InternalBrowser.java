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
import minium.web.WebElements;
import minium.web.WebLocator;
import minium.web.actions.Browser;
import minium.web.internal.InternalWebElements;

import org.openqa.selenium.OutputType;

import com.google.common.io.Files;

public class InternalBrowser<T extends WebElements> implements Browser<T> {

    class InternalNavigation implements Browser.Navigation {

        @Override
        public void back() {
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().back();
                }
            }.perform();
        }

        @Override
        public void forward() {
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().forward();
                }
            }.perform();
        }

        @Override
        public void to(final String url) {
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().to(url);
                }
            }.perform();
        }

        @Override
        public void to(final URL url) {
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().to(url);
                }
            }.perform();
        }

        @Override
        public void refresh() {
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    documentDriver().navigate().refresh();
                }
            }.perform();
        }

    }

    class InternalWindow implements Browser.Window {

        @Override
        public void setSize(final Dimension targetSize) {
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    org.openqa.selenium.Dimension seleniumSize = new org.openqa.selenium.Dimension(targetSize.width(), targetSize.height());
                    documentDriver().manage().window().setSize(seleniumSize);
                }
            }.perform();
        }

        @Override
        public void setPosition(final Point targetPosition) {
            new AbstractInteraction(elems) {
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
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    documentDriver().manage().window().maximize();
                }
            }.perform();
        }

    }

    class InternalOptions implements Browser.Options {

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
            return new InternalWindow();
        }

    }

    class InternalScreenshot implements Browser.Screenshot {

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

    private final Elements elems;
    private final WebLocator<T> locator;

    /**
     * @param defaultHasBrowser
     */
    public InternalBrowser(Elements elems, WebLocator<T> locator) {
        this.elems = elems;
        this.locator = locator;
    }

    @Override
    public WebLocator<T> locator() {
        return locator;
    }

    @Override
    public void get(final String url) {
        new AbstractInteraction(elems) {
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
        new AbstractInteraction(elems) {
            @Override
            protected void doPerform() {
                documentDriver().close();
            }
        }.perform();
    }

    @Override
    public void quit() {
        new AbstractInteraction(elems) {
            @Override
            protected void doPerform() {
                documentDriver().quit();
            }
        }.perform();
    }

    @Override
    public Navigation navigate() {
        return new InternalNavigation();
    }

    @Override
    public Options manage() {
        return new InternalOptions();
    }

    @Override
    public Screenshot screenshot() {
        return new InternalScreenshot();
    }

    protected DocumentWebDriver documentDriver() {
        return elems.as(InternalWebElements.class).documentDriver();
    }
}