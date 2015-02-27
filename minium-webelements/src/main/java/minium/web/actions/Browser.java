package minium.web.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;

import minium.Dimension;
import minium.Point;
import minium.web.WebElements;

public interface Browser<T extends WebElements> {

    interface Options {

        CookieCollection cookies();

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

    interface CookieCollection extends Iterable<Cookie> {
        CookieCollection add(Cookie cookie);

        CookieCollection remove(String name);

        CookieCollection remove(Cookie cookie);

        CookieCollection clear();

        Cookie get(String name);

        public Options done();
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

    T root();

    T of(WebElements ... elems);

    void get(String url);

    String getCurrentUrl();

    String getTitle();

    void close();

    void quit();

    Navigation navigate();

    Options manage();

    Screenshot screenshot();
}
