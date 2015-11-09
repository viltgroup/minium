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
package minium.cucumber.data.reader;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataDTO {

    private List<String> headers;
    private Map<Integer, List<String>> values;

    public DataDTO() {
        super();
        this.headers = Lists.newArrayList();
        this.values = Maps.newHashMap();
    }

    public void addLineValues(Integer lineNum, List<String> values) {
        this.values.put(lineNum, values);
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public Map<Integer, List<String>> getValues() {
        return values;
    }

    public void setValues(Map<Integer, List<String>> values) {
        this.values = values;
    }

}
