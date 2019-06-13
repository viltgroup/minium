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
package minium.web.internal.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import minium.Dimension;
import minium.FindElements;
import minium.Point;
import minium.actions.Configuration;
import minium.actions.Duration;
import minium.actions.ExceptionHandler;
import minium.actions.HasConfiguration;
import minium.actions.InteractionListener;
import minium.actions.internal.AbstractInteraction;
import minium.internal.HasElementsFactory;
import minium.web.BasicWebElements;
import minium.web.DocumentWebDriver;
import minium.web.TargetLocatorWebElements;
import minium.web.WebElements;
import minium.web.actions.Browser;
import minium.web.actions.Cookie;
import minium.web.actions.WebConfiguration;
import minium.web.internal.InternalWebElements;
import minium.web.internal.WebElementsFactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import platypus.Mixin;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;

public class InternalBrowser<T extends WebElements> implements Browser<T> {

    @SuppressWarnings("serial")
    private final TypeToken<T> typeVariableToken = new TypeToken<T>(getClass()) { };

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

    class InternalWindow implements WebConfiguration.Window {

        @Override
        public WebConfiguration.Window setSize(final Dimension targetSize) {
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    org.openqa.selenium.Dimension seleniumSize = new org.openqa.selenium.Dimension(targetSize.width(), targetSize.height());
                    documentDriver().manage().window().setSize(seleniumSize);
                }
            }.perform();
            return this;
        }

        @Override
        public WebConfiguration.Window setPosition(final Point targetPosition) {
            new AbstractInteraction(elems) {
                @Override
                protected void doPerform() {
                    org.openqa.selenium.Point seleniumPoint = new org.openqa.selenium.Point(targetPosition.x(), targetPosition.y());
                    documentDriver().manage().window().setPosition(seleniumPoint);
                }
            }.perform();
            return this;
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

    class InternalWebConfiguration extends Mixin.Impl implements WebConfiguration, Configuration {

        @Override
        public CookieCollection cookies() {
            return new InternalCookieCollection();
        }

        @Override
        public Window window() {
            return new InternalWindow();
        }

        @Override
        public Duration defaultTimeout() {
            return getConfiguration().defaultTimeout();
        }

        @Override
        public Configuration defaultTimeout(Duration defaultTimeout) {
            getConfiguration().defaultTimeout(defaultTimeout);
            return this;
        }

        @Override
        public Configuration defaultTimeout(long time, TimeUnit unit) {
            getConfiguration().defaultTimeout(time, unit);
            return this;
        }

        @Override
        public Duration defaultInterval() {
            return getConfiguration().defaultInterval();
        }

        @Override
        public Configuration defaultInterval(Duration defaultInterval) {
            getConfiguration().defaultInterval(defaultInterval);
            return this;
        }

        @Override
        public Configuration defaultInterval(long time, TimeUnit unit) {
            getConfiguration().defaultInterval(time, unit);
            return this;
        }

        @Override
        public WaitingPreset waitingPreset(final String preset) {
            final WaitingPreset waitingPreset = getConfiguration().waitingPreset(preset);
            return new WaitingPreset() {

                @Override
                public Duration timeout() {
                    return waitingPreset.timeout();
                }

                @Override
                public WaitingPreset timeout(long time, TimeUnit unit) {
                    waitingPreset.timeout(time, unit);
                    return this;
                }

                @Override
                public WaitingPreset timeout(Duration timeout) {
                    waitingPreset.timeout(timeout);
                    return this;
                }

                @Override
                public WaitingPreset reset() {
                    waitingPreset.reset();
                    return this;
                }

                @Override
                public Duration interval() {
                    return waitingPreset.interval();
                }

                @Override
                public WaitingPreset interval(long time, TimeUnit unit) {
                    waitingPreset.interval(time, unit);
                    return this;
                }

                @Override
                public WaitingPreset interval(Duration interval) {
                    waitingPreset.interval(interval);
                    return this;
                }

                @Override
                public Configuration done() {
                    return InternalWebConfiguration.this;
                }
            };
        }

        @Override
        public InteractionListenerCollection interactionListeners() {
            final InteractionListenerCollection interactionListeners = getConfiguration().interactionListeners();
            return new InteractionListenerCollection() {

                @Override
                public Iterator<InteractionListener> iterator() {
                    return interactionListeners.iterator();
                }

                @Override
                public InteractionListenerCollection remove(InteractionListener interactionListener) {
                    interactionListeners.remove(interactionListener);
                    return this;
                }

                @Override
                public Configuration done() {
                    return InternalWebConfiguration.this;
                }

                @Override
                public InteractionListenerCollection clear() {
                    interactionListeners.clear();
                    return this;
                }

                @Override
                public InteractionListenerCollection add(InteractionListener interactionListener) {
                    interactionListeners.add(interactionListener);
                    return this;
                }
            };
        }

        @Override
        public ExceptionHandlerCollection exceptionHandlers() {
            final ExceptionHandlerCollection exceptionHandlers = getConfiguration().exceptionHandlers();
            return new ExceptionHandlerCollection() {

                @Override
                public Iterator<ExceptionHandler> iterator() {
                    return exceptionHandlers.iterator();
                }

                @Override
                public ExceptionHandlerCollection add(ExceptionHandler exceptionHandler) {
                    exceptionHandlers.add(exceptionHandler);
                    return this;
                }

                @Override
                public ExceptionHandlerCollection remove(ExceptionHandler exceptionHandler) {
                    exceptionHandlers.remove(exceptionHandler);
                    return this;
                }

                @Override
                public ExceptionHandlerCollection clear() {
                    exceptionHandlers.clear();
                    return this;
                }

                @Override
                public Configuration done() {
                    return InternalWebConfiguration.this;
                }

            };
        }

        private Configuration getConfiguration() {
            return elems.as(HasConfiguration.class).configure();
        }
    }

    class InternalCookieCollection implements WebConfiguration.CookieCollection {

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
        public WebConfiguration.CookieCollection add(Cookie cookie) {
            return this;
        }

        @Override
        public WebConfiguration.CookieCollection remove(String name) {
            documentDriver().manage().deleteCookieNamed(name);
            return this;
        }

        @Override
        public WebConfiguration.CookieCollection remove(Cookie cookie) {
            return remove(cookie.getName());
        }

        @Override
        public WebConfiguration.CookieCollection clear() {
            documentDriver().manage().deleteAllCookies();
            return this;
        }

        @Override
        public Cookie get(String name) {
            org.openqa.selenium.Cookie nativeCookie = documentDriver().manage().getCookieNamed(name);
            return nativeCookie == null ? null : new InternalCookie(nativeCookie);
        }

        @Override
        public WebConfiguration done() {
            return configure();
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
            Files.asByteSource(asFile()).copyTo(Files.asByteSink(file));
        }

        @Override
        public void saveTo(String path) throws IOException {
            File file = new File(path);
            saveTo(file);
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
        this.elems = elems;
    }

    @Override
    public T root() {
        return elems.as(TargetLocatorWebElements.class).documentRoots().as(typeVariableToken);
    }

    // CHECKSTYLE:OFF
    @Override
    public T $(String selector) {
        return root().as(FindElements.class).find(selector).as(typeVariableToken);
    }

    @Override
    public T $(WebElements... elems) {
        if (elems == null || elems.length == 0) return factory.createEmpty(documentDriver());

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
    // CHECKSTYLE:ON

    @Override
    public void get(final String url) {
        new GetInteraction(elems, url).perform();
    }

    @Override
    public String getCurrentUrl() {
        return documentDriver().getCurrentUrl();
    }

    @Override
    public String getPerformance() {
        Map<?, ?> performance = (Map<?, ?>) ((JavascriptExecutor) documentDriver()).executeScript("return window.performance");
        List<LogEntry> jsErrors = documentDriver().manage().logs().get(LogType.BROWSER).filter(Level.SEVERE);
        return "{ \"data\": " + performance + "\", \"jsErrors\": " + jsErrors + "}";
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
        documentDriver().quit();
    }

    @Override
    public Navigation navigate() {
        return new InternalNavigation();
    }

    @Override
    public WebConfiguration configure() {
        return new InternalWebConfiguration();
    }

    @Override
    public Screenshot screenshot() {
        return new InternalScreenshot();
    }

    protected DocumentWebDriver documentDriver() {
        return elems.as(InternalWebElements.class).documentDriver();
    }
}