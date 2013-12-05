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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class AppPreferences implements Preferences {

    private static final String MINIUM_HOME_KEY = "minium.home";

    private ObjectMapper mapper;
    private JsonNode rootNode;

    private File baseDir;

    public AppPreferences() throws IOException {
        this(new File(System.getProperty(MINIUM_HOME_KEY), "minium-prefs.json"));
    }

    public AppPreferences(File file) throws IOException {
        this(new BufferedReader(new FileReader(file)));
        baseDir = file.getParentFile();
    }

    public AppPreferences(Reader reader) throws IOException {
        mapper = new ObjectMapper();
        rootNode = mapper.readTree(reader);
    }

    @Override
    public File getBaseDir() {
        return baseDir;
    }

    public <T> T get(String property, Class<T> clazz) {
        return get(property, clazz, null);
    }

    public <T> List<T> getList(String property, Class<T> clazz) {
        return getList(property, clazz, null);
    }

    public <T> T get(String property, Class<T> clazz, T defaultVal) {
        try {
            JsonNode jsonNode = rootNode.get(property);
            return getValue(jsonNode, clazz, defaultVal);
        } catch (IOException e) {
            throw propagate(e);
        }
    }

    public <T> List<T> getList(String property, Class<T> clazz, T defaultVal) {
        try {
            JsonNode jsonNode = rootNode.get(property);
            Preconditions.checkState(jsonNode.isArray());

            int size = jsonNode.size();
            List<T> values = Lists.newArrayListWithCapacity(size);

            for (int i = 0; i < size; i++) {
                JsonNode child = jsonNode.get(i);
                values.add(getValue(child, clazz, defaultVal));
            }

            return values;
        } catch (IOException e) {
            throw propagate(e);
        }
    }

    protected <T> T getValue(JsonNode jsonNode, Class<T> clazz, T defaultVal) throws IOException {
        if (jsonNode == null)
            return defaultVal;
        T value = mapper.treeToValue(jsonNode, clazz);
        maybeSetAppPreferences(value);
        return value == null ? defaultVal : value;
    }

    protected <T> void fillValue(JsonNode jsonNode, T obj) throws IOException {
        if (jsonNode == null)
            return;
        ObjectReader reader = mapper.readerForUpdating(obj);
        reader.readValue(jsonNode);
        maybeSetAppPreferences(obj);
    }

    protected <T> void maybeSetAppPreferences(T obj) {
        if (obj instanceof BasePreferences) {
            ((BasePreferences) obj).setAppPreferences(this);
        }
    }

    public JsonNode asJson() {
        return rootNode;
    }
}
