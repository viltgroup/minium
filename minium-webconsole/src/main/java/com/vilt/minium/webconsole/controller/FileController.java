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
package com.vilt.minium.webconsole.controller;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.google.common.base.Charsets;

@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/file")
public class FileController implements DisposableBean {

    private FileDialog fileDialog;

    public FileController() throws IOException {
        fileDialog = new FileDialog((Frame) null, "");
        fileDialog.toFront();
        fileDialog.setAlwaysOnTop(true);
        fileDialog.setFilenameFilter(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".js");
            }
        });
    }

    @RequestMapping("/open")
    @ResponseBody
    public TextFileResult openFile() throws IOException {
        fileDialog.setTitle("Open Javascript File");
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setFile("*.js");
        fileDialog.setVisible(true);
        if (fileDialog.getFile() != null) {
            File file = new File(fileDialog.getDirectory(), fileDialog.getFile());
            return new TextFileResult(file);
        } else {
            throw new CanceledException("Open file operation was cancelled");
        }
    }

    @RequestMapping(value = "/save", method = POST)
    @ResponseBody
    public TextFilePathResult save(@RequestBody TextFileResult fileResult) throws IOException {
        File file;
        if (StringUtils.isEmpty(fileResult.getFilePath())) {
            fileDialog.setTitle("Save Javascript File");
            fileDialog.setMode(FileDialog.SAVE);
            fileDialog.setVisible(true);
            if (fileDialog.getFile() != null) {
                file = new File(fileDialog.getDirectory(), fileDialog.getFile());
            } else {
                throw new CanceledException("Save file operation was cancelled");
            }
        } else {
            file = new File(fileResult.getFilePath());
        }

        FileUtils.write(file, fileResult.getContent(), Charsets.UTF_8.name());

        return new TextFilePathResult(file);
    }

    @Override
    public void destroy() throws Exception {
        fileDialog.dispose();
    }
}
