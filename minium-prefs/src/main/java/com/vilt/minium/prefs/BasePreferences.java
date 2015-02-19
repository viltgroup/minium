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
package com.vilt.minium.prefs;

import static com.google.common.base.Throwables.propagate;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public abstract class BasePreferences implements Preferences {

    private static final String MINIUM_HOME_KEY = "minium.home";

    private AppPreferences appPreferences;

    protected void setAppPreferences(AppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }

    @Override
    public File getBaseDir() {
        return appPreferences == null ? getDefaultPrefsFile().getParentFile() : appPreferences.getBaseDir();
    }

    @Override
    public void validate() {
    }

    protected File oneOf(File... files) {
        for (File file : files) {
            if (file.exists())
                return file;
        }

        return null;
    }

    protected static File getDefaultPrefsFile() {
        try {
            if (System.getProperty(MINIUM_HOME_KEY) == null) {
                URL resource = AppPreferences.class.getClassLoader().getResource("minium-prefs.json");
                return new File(resource.toURI());
            } else {
                return new File(System.getProperty(MINIUM_HOME_KEY), "minium-prefs.json");
            }
        } catch (URISyntaxException e) {
            throw propagate(e);
        }
    }
}
