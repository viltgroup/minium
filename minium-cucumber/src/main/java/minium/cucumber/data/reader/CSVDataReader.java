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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

public class CSVDataReader implements DataReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVDataReader.class);

    public CSVDataReader() {
    }

    @Override
    public DataDTO readTable(InputStream inputStream) {
        InputStreamReader fileReader = null;
        CSVParser csvFileParser = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;

        DataDTO dataDTO = new DataDTO();
        try {
            fileReader = new InputStreamReader(inputStream, Charsets.UTF_8);
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            List<CSVRecord> csvRecords = csvFileParser.getRecords();

            // Read the CSV file records starting from the second record to skip
            // the header
            for (int i = 0; i < csvRecords.size(); i++) {
                List<String> lineValues = Lists.newArrayList();
                CSVRecord record = csvRecords.get(i);
                for (Iterator<String> iterator = record.iterator(); iterator.hasNext();) {
                    lineValues.add(iterator.next());
                }
                dataDTO.addLineValues(i, lineValues);
            }

        } catch (Exception e) {
            LOGGER.error("Error in CsvFileReader -  {}", e.getMessage());
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                LOGGER.warn("Error while closing fileReader/csvFileParser -  {}", e.getMessage());
            }
        }
        return dataDTO;
    }

    @Override
    public DataDTO readExamples(InputStream inputStream) {
        InputStreamReader fileReader = null;
        CSVParser csvFileParser = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT;

        DataDTO dataDTO = new DataDTO();
        try {
            fileReader = new InputStreamReader(inputStream, Charsets.UTF_8);
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            List<CSVRecord> csvRecords = csvFileParser.getRecords();

            // get headers
            if (!csvRecords.isEmpty()) {
                List<String> header = Lists.newArrayList(csvRecords.get(0));
                dataDTO.setHeaders(header);
            }

            // Read the CSV file records starting from the second record to skip
            // the header
            for (int i = 1; i < csvRecords.size(); i++) {
                List<String> lineValues = Lists.newArrayList();
                CSVRecord record = csvRecords.get(i);
                for (Iterator<String> iterator = record.iterator(); iterator.hasNext();) {
                    lineValues.add(iterator.next());
                }
                dataDTO.addLineValues(i, lineValues);
            }

        } catch (Exception e) {
            LOGGER.error("Error in CsvFileReader -  {}", e.getMessage());
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                LOGGER.warn("Error while closing fileReader/csvFileParser -  {}", e.getMessage());
            }
        }
        return dataDTO;
    }

}
