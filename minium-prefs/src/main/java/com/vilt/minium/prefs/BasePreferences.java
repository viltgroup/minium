package com.vilt.minium.prefs;

import java.io.File;

public abstract class BasePreferences implements Preferences {
    
    private AppPreferences appPreferences;

    protected void setAppPreferences(AppPreferences appPreferences) {
        this.appPreferences = appPreferences;
    }
    
    @Override
    public File getBaseDir() {
        return appPreferences == null ? new File(System.getProperty("user.dir")) : appPreferences.getBaseDir();
    }
}
