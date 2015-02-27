package minium.web.internal.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

import minium.Dimension;
import minium.Point;
import minium.actions.internal.AbstractInteraction;
import minium.internal.HasElementsFactory;
import minium.web.BasicWebElements;
import minium.web.DocumentWebDriver;
import minium.web.TargetLocatorWebElements;
import minium.web.WebElements;
import minium.web.actions.Browser;
import minium.web.internal.InternalWebElements;
import minium.web.internal.WebElementsFactory;

import org.openqa.selenium.OutputType;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;

public class InternalBrowser<T extends WebElements> implements Browser<T> {

    @SuppressWarnings("serial")
    private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) {};

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
        public CookieCollection cookies() {
            return new InternalCookieCollection();
        }

        @Override
        public Window window() {
            return new InternalWindow();
        }
    }

    class InternalCookieCollection implements CookieCollection {

        @Override
        public Iterator<Cookie> iterator() {
            return FluentIterable.from(documentDriver().manage().getCookies())
                    .transform(new Function<org.openqa.selenium.Cookie, Cookie>() {

                        @Override
                        public Cookie apply(org.openqa.selenium.Cookie nativeCookie) {
                            return new InternalCookie(nativeCookie);
                        }
                    }).iterator();
        }

        @Override
        public CookieCollection add(Cookie cookie) {
            return this;
        }

        @Override
        public CookieCollection remove(String name) {
            documentDriver().manage().deleteCookieNamed(name);
            return this;
        }

        @Override
        public CookieCollection remove(Cookie cookie) {
            return remove(cookie.getName());
        }

        @Override
        public CookieCollection clear() {
            documentDriver().manage().deleteAllCookies();
            return this;
        }

        @Override
        public Cookie get(String name) {
            org.openqa.selenium.Cookie nativeCookie = documentDriver().manage().getCookieNamed(name);
            return new InternalCookie(nativeCookie);
        }

        @Override
        public Options done() {
            return manage();
        }
    }

    class InternalCookie implements Cookie {

        private org.openqa.selenium.Cookie nativeCookie;

        public InternalCookie(org.openqa.selenium.Cookie nativeCookie) {
            this.nativeCookie = nativeCookie;
        }

        @Override
        public String getName() {
            return nativeCookie.getName();
        }

        @Override
        public String getValue() {
            return nativeCookie.getValue();
        }

        @Override
        public String getDomain() {
            return nativeCookie.getDomain();
        }

        @Override
        public String getPath() {
            return nativeCookie.getPath();
        }

        @Override
        public boolean isSecure() {
            return nativeCookie.isSecure();
        }

        @Override
        public boolean isHttpOnly() {
            return nativeCookie.isHttpOnly();
        }

        @Override
        public Date getExpiry() {
            return nativeCookie.getExpiry();
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

    private final WebElementsFactory<T> factory;
    private final T elems;

    /**
     * @param defaultHasBrowser
     */
    public InternalBrowser(WebElementsFactory<T> factory) {
        this.factory = factory;
        this.elems = factory.createRoot();
    }

    /**
     * @param defaultHasBrowser
     */
    @SuppressWarnings("unchecked")
    public InternalBrowser(T elems) {
        this.factory = (WebElementsFactory<T>) elems.as(HasElementsFactory.class).factory();
        this.elems = elems;;
    }

    @Override
    public T root() {
        return elems.as(TargetLocatorWebElements.class).documentRoots().as(typeVariableToken);
    }

    @Override
    public T of(WebElements... elems) {
        if (elems.length == 0) return factory.createNative();

        T result = null;
        for (WebElements webElements : elems) {
            if (result == null) {
                result = webElements.as(typeVariableToken);
            } else {
                result = result.as(BasicWebElements.class).add(webElements).as(typeVariableToken);
            }
        }
        return result;
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