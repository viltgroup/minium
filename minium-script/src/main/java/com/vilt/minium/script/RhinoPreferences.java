package com.vilt.minium.script;

import java.io.File;
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
        return Collections.singletonList(modulesDir.toURI().toString());
    }

    public void setModulePath(List<String> modulePath) {
        this.modulePath = modulePath;
    }
}
