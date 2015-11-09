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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

public class DataReaderFactory {

    public enum Format {
        CSV, XLS, XLSX
    }

    private static final Map<Format, Class> factoryMap = Collections.unmodifiableMap(new HashMap<Format, Class>() {
        {
            put(Format.CSV, CSVDataReader.class);
            put(Format.XLS, ExcelDataReader.class);
            put(Format.XLSX, ExcelDataReader.class);
        }
    });

    public static DataReader create(String fileName) throws InstantiationException, IllegalAccessException {
        Format reader = getFormat(fileName);
        return (DataReader) factoryMap.get(reader).newInstance();
    }

    private static Format getFormat(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toUpperCase();
        return Format.valueOf(ext);
    }
}
