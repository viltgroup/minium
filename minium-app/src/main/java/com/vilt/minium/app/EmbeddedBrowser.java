/*
 * Copyright (C) 2013 The Minium Authors
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
package com.vilt.minium.app;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.collect.Lists;
import com.vilt.minium.prefs.WebConsolePreferences;

public class EmbeddedBrowser {

    public interface Listener {
        public void closed();
    }

    private static class StreamGobbler extends Thread {
        InputStream is;

        private StreamGobbler(InputStream is) {
            this.is = is;
        }

        @Override
        public void run() {
            try {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                do {
                    line = br.readLine();
                } while (line != null);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    private List<Listener> listeners = Lists.newArrayList();
    private File baseDir;
    private WebConsolePreferences webConsolePreferences;

    public EmbeddedBrowser(File baseDir, WebConsolePreferences webConsolePreferences) {
        this(baseDir, webConsolePreferences, null);
    }

    public EmbeddedBrowser(File baseDir, WebConsolePreferences webConsolePreferences, Listener listener) {
        webConsolePreferences.validate();
        this.baseDir = baseDir;
        this.webConsolePreferences = webConsolePreferences;
        if (listener != null) {
            addListener(listener);
        }
    }

    public void addListener(Listener listener) {
        checkNotNull(listener);
        listeners.add(listener);
    }

    public void start() throws IOException {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String browserExecPath = webConsolePreferences.getChromeBin().getAbsolutePath();

                    String host = webConsolePreferences.getHost();
                    int port = webConsolePreferences.getPort();

                    File userDataDir = new File(baseDir, "user-data");

                    Process p = new ProcessBuilder(browserExecPath, format("--app=http://%s:%d/", host, port), "--disable-background-mode",
                            format("--user-data-dir=%s", userDataDir.getAbsolutePath())).start();

                    StreamGobbler inGobbler = new StreamGobbler(p.getInputStream());
                    StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream());

                    // start gobblers
                    inGobbler.start();
                    errorGobbler.start();

                    p.waitFor();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    fireListeners();
                }
            }

        }).start();
    }

    private void fireListeners() {
        for (Listener listener : listeners) {
            listener.closed();
        }
    }
}
