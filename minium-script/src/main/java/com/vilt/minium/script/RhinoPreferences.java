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
package com.vilt.minium.script;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.vilt.minium.prefs.AppPreferences;
import com.vilt.minium.prefs.BasePreferences;

public class RhinoPreferences extends BasePreferences {
    private List<String> modulePath;

    public static RhinoPreferences from(AppPreferences preferences) {
        return preferences == null ? new RhinoPreferences() : preferences.get("rhino", RhinoPreferences.class);
    }

    public List<String> getModulePath() {
        return modulePath == null ? getDefaultModulePath() : modulePath;
    }

    protected List<String> getDefaultModulePath() {
        File modulesDir = new File(getBaseDir(), "modules");
        return Collections.singletonList(modulesDir.getAbsolutePath());
    }

    public void setModulePath(List<String> modulePath) {
        this.modulePath = modulePath;
    }

    public void setModulePath(String ... modulePath) {
        setModulePath(Arrays.asList(modulePath));
    }
}
