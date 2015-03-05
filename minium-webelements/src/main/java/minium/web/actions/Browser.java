package minium.web.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import minium.web.WebElements;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public interface Browser<T extends WebElements> {

    /**
     * <p>This is a Minium adapter for {@link WebDriver.Navigation}</p>
     *
     * @see org.openqa.selenium.WebDriver.Navigation
     * @author rui.figueira
     */
    interface Navigation {

        /**
         * Move back a single "item" in the browser's history.
         *
         * @see WebDriver.Navigation#back()
         */
        void back();

        /**
         * Move a single "item" forward in the browser's history. Does nothing if we are on the latest page viewed.
         *
         * @see WebDriver.Navigation#forward()
         */
        void forward();

        /**
         * Load a new web page in the current browser window. This is done using an HTTP GET operation, and the method
         * will block until the load is complete. This will follow redirects issued either by the server or as a
         * meta-redirect from within the returned HTML. Should a meta-redirect "rest" for any duration of time,
         * it is best to wait until this timeout is over, since should the underlying page change whilst your test
         * is executing the results of future calls against this interface will be against the freshly loaded page.
         *
         * @param url the URL to load. It is best to use a fully qualified URL
         *
         * @see WebDriver.Navigation#to(String)
         */
        void to(String url);

        /**
         * Overloaded version of {@link #to(String)} that makes it easy to pass in a URL.
         *
         * @param url the URL to load.
         */
        void to(URL url);

        /**
         * Refresh the current page.
         */
        void refresh();
    }

    /**
     * <p>Allows screenshots to be taken from this browser. Images can be stored in a file or
     * in memory. In both cases, they are stored in <code>png</code> format. It is a Minium adapter
     * for Selenium {@link TakesScreenshot}</p>
     *
     * @see TakesScreenshot
     * @author rui.figueira
     */
    interface Screenshot {

        /**
         * Gets screenshot data in memory as a byte array in <code>png</code> format.
         *
         * @return byte array with image data in <code>png</code> format
         */
        byte[] asBytes();

        /**
         * Gets screenshot as a file. Notice that, as described in {@link OutputType#FILE},
         * file is deleted on exit, so ensure that you either read data to another structure
         * or you just copy the file elsewhere.
         *
         * @return file file with <code>png</code> format.
         */
        File asFile();

        /**
         * Saves screenshot data to a specified file. In case you need to keep the file after this
         * process exits, this is the recommended way.
         *
         * @param file file where screenshot will be saved with <code>png</code> format.
         */
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

    WebConfiguration configure();

    Screenshot screenshot();
}
