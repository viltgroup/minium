package minium.web.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Set;

import minium.Dimension;
import minium.Point;
import minium.web.WebElements;
import minium.web.WebLocator;

public interface Browser<T extends WebElements> {

    interface Options {

        void addCookie(Cookie cookie);

        void deleteCookieNamed(String name);

        void deleteCookie(Cookie cookie);

        void deleteAllCookies();

        Set<Cookie> getCookies();

        Cookie getCookieNamed(String name);

        Window window();
    }

    interface Navigation {

        void back();

        void forward();

        void to(String url);

        void to(URL url);

        void refresh();
    }

    interface Window {

        void setSize(Dimension targetSize);

        void setPosition(Point targetPosition);

        Dimension getSize();

        Point getPosition();

        void maximize();
    }

    interface Cookie {

        String getName();

        String getValue();

        String getDomain();

        String getPath();

        boolean isSecure();

        boolean isHttpOnly();

        Date getExpiry();
    }

    interface Screenshot {

        byte[] asBytes();

        File asFile();

        void saveTo(File file) throws IOException;
    }

    WebLocator<T> locator();

    void get(String url);

    String getCurrentUrl();

    String getTitle();

    void close();

    void quit();

    Navigation navigate();

    Options manage();

    Screenshot screenshot();
}
