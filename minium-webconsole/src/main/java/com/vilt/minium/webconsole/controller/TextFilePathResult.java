package com.vilt.minium.webconsole.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class TextFilePathResult implements Serializable {

    private static final long serialVersionUID = 5206630144168166026L;
    
    private String filePath;
    
    public TextFilePathResult() {
    }
    
    public TextFilePathResult(File file) throws IOException {
        filePath = file.getAbsolutePath();
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
}
