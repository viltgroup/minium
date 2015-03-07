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
package minium.cucumber.rest.dto;

import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.Row.DiffType;

import java.util.ArrayList;
import java.util.List;

import cucumber.api.DataTable;
import cucumber.runtime.table.TableConverter;

public class DataTableDTO {

    public static class DataTableRowDTO {

        private List<String> cells;
        private List<CommentDTO> comments;
        private Integer line;
        private DiffType diffType;

        public DataTableRowDTO() {
        }

        public DataTableRowDTO(DataTableRow row) {
            cells = row.getCells();
            comments = CommentDTO.fromGherkinComments(row.getComments());
            line = row.getLine();
            diffType = row.getDiffType();
        }

        public List<String> getCells() {
            return cells;
        }

        public void setCells(List<String> cells) {
            this.cells = cells;
        }

        public List<CommentDTO> getComments() {
            return comments;
        }

        public void setComments(List<CommentDTO> comments) {
            this.comments = comments;
        }

        public Integer getLine() {
            return line;
        }

        public void setLine(Integer line) {
            this.line = line;
        }

        public DiffType getDiffType() {
            return diffType;
        }

        public void setDiffType(DiffType diffType) {
            this.diffType = diffType;
        }

        public DataTableRow toDataTableRow() {
            return new DataTableRow(CommentDTO.toGherkinComments(comments), cells, line, diffType);
        }
    }

    private List<DataTableRowDTO> rows = new ArrayList<DataTableRowDTO>();

    public DataTableDTO() {
    }

    public DataTableDTO(DataTable dataTable) {
        List<DataTableRow> gherkinRows = dataTable.getGherkinRows();
        for (DataTableRow gherkinRow : gherkinRows) {
            rows.add(new DataTableRowDTO(gherkinRow));
        }
    }

    public List<DataTableRowDTO> getRows() {
        return rows;
    }

    public void setRows(List<DataTableRowDTO> rows) {
        this.rows = rows;
    }

    public DataTable toDataTable(TableConverter tableConverter) {
        List<DataTableRow> gherkinRows = new ArrayList<DataTableRow>();
        for (DataTableRowDTO row : rows) {
            gherkinRows.add(row.toDataTableRow());
        }
        return new DataTable(gherkinRows, tableConverter);
    }
}
