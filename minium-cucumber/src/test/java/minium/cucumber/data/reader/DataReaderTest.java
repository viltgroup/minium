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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.google.common.io.Files;

public class DataReaderTest {

    @Test
    public void readData() throws IOException, InstantiationException, IllegalAccessException {
        String filePath = "data.csv";
        File file = getResource(filePath);
        InputStream inputStream = Files.asByteSource(file).openStream();
        DataReader dataReader = DataReaderFactory.create(filePath);
        DataDTO dataRead = dataReader.readExamples(inputStream);

        assertEquals(7, dataRead.getValues().keySet().size());
    }

    @Test
    public void readDataTable() throws IOException, InstantiationException, IllegalAccessException {
        String filePath = "data-table.csv";
        File file = getResource(filePath);
        InputStream inputStream = Files.asByteSource(file).openStream();
        DataReader dataReader = DataReaderFactory.create(filePath);
        DataDTO dataRead = dataReader.readTable(inputStream);

        assertEquals(3, dataRead.getValues().keySet().size());
    }

    @Test
    public void readXLSXDataTable() throws IOException, InstantiationException, IllegalAccessException {
        String filePath = "data.xlsx";
        File file = getResource(filePath);
        InputStream inputStream = Files.asByteSource(file).openStream();
        DataReader dataReader = DataReaderFactory.create(filePath);
        DataDTO dataRead = dataReader.readExamples(inputStream);

        assertEquals(3, dataRead.getValues().keySet().size());
    }

    @Test
    public void readXLSDataTable() throws IOException, InstantiationException, IllegalAccessException {
        String filePath = "data.xls";
        File file = getResource(filePath);
        InputStream inputStream = Files.asByteSource(file).openStream();
        DataReader dataReader = DataReaderFactory.create(filePath);
        DataDTO dataRead = dataReader.readExamples(inputStream);

        assertEquals(3, dataRead.getValues().keySet().size());
    }

    private File getResource(String filePath) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(filePath).getFile());
    }
}
