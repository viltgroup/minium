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

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class ExcelDataReader implements DataReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelDataReader.class);

    @Override
    public DataDTO readExamples(InputStream inputStream) {
        DataDTO dataDTO = new DataDTO();
        try {
            InputStream file = inputStream;

            // Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            // Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            // Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();

            int i = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                // For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                if (i == 0) {
                    List<String> header = Lists.newArrayList();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        header.add(cell.getStringCellValue());
                    }
                    dataDTO.setHeaders(header);
                } else {
                    List<String> lineValues = Lists.newArrayList();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        lineValues.add(cell.getStringCellValue());
                    }

                    dataDTO.addLineValues(i, lineValues);
                }
                i++;
            }
            file.close();
        } catch (Exception e) {
            LOGGER.error("Error Reading Excell -  {}", e.getMessage());
        }
        return dataDTO;
    }

    @Override
    public DataDTO readTable(InputStream inputStream) {
        // TODO Auto-generated method stub
        return null;
    }

}
