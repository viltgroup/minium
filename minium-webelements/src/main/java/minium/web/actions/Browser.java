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
package minium.web.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import minium.web.WebElements;

/**
 * Browser can be seen as a {@link org.openqa.selenium.WebDriver} wrapper.
 *
 * @author rui.figueira
 *
 * @param <T> {@link WebElements} type for chaining calls
 */
public interface Browser<T extends WebElements> {

    /**
     * <p>This is a Minium adapter for {@link org.openqa.selenium.WebDriver.Navigation}</p>
     *
     * @see org.openqa.selenium.WebDriver.Navigation
     * @author rui.figueira
     */
    interface Navigation {

        /**
         * Move back a single "item" in the browser's history.
         *
         * @see org.openqa.selenium.WebDriver.Navigation#back()
         */
        void back();

        /**
         * Move a single "item" forward in the browser's history. Does nothing if we are on the latest page viewed.
         *
         * @see org.openqa.selenium.WebDriver.Navigation#forward()
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
         * @see org.openqa.selenium.WebDriver.Navigation#to(String)
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
     * for Selenium {@link org.openqa.selenium.TakesScreenshot}</p>
     *
     * @see org.openqa.selenium.TakesScreenshot
     * @author rui.figueira
     */
    interface Screenshot {

        /**
         * Gets screenshot data in memory as a byte array in <code>png</code> format.
         *
         * @return byte array with image data in {@code png} format
         */
        byte[] asBytes();

        /**
         * Gets screenshot as a file. Notice that, as described in
         * {@link org.openqa.selenium.OutputType#FILE}, file is deleted on exit, so ensure that
         * you either read data to another structure or you just copy the file elsewhere.
         *
         * @return file file with {@code png} format.
         */
        File asFile();

        /**
         * Saves screenshot data to a specified file. In case you need to keep the file after this
         * process exits, this is the recommended way.
         *
         * @param file file where screenshot will be saved with {@code png} format.
         * @throws IOException if some IO problem occurs saving to the specified file
         */
        void saveTo(File file) throws IOException;

        /**
         * Saves screenshot to the specified path.
         *
         * @param path Path to the file where screenshot will be saved with {@code png} format.
         * @throws IOException if some IO problem occurs saving to the specified file
         */
        void saveTo(String path) throws IOException;
    }

    T root();

    // CHECKSTYLE:OFF
    T $(String selector);

    T $(WebElements ... elems);
    // CHECKSTYLE:ON

    void get(String url);

    String getCurrentUrl();

    String getPerformance();

    String getTitle();

    void close();

    void quit();

    Navigation navigate();

    WebConfiguration configure();

    Screenshot screenshot();
}
