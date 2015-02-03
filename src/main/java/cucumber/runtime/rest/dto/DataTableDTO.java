package cucumber.runtime.rest.dto;

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
