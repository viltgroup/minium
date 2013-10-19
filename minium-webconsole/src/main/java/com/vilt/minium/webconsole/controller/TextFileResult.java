package com.vilt.minium.webconsole.controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Charsets;

public class TextFileResult implements Serializable {

    private static final long serialVersionUID = -7621187890829925395L;
    
    private String filePath;
    private String content;
    
    public TextFileResult() {
    }
    
    public TextFileResult(File file) throws IOException {
        filePath = file.getAbsolutePath();
        content = FileUtils.readFileToString(file, Charsets.UTF_8.name());
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
}
