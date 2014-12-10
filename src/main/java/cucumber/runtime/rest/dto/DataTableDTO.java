package cucumber.runtime.rest.dto;

import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.Row.DiffType;

import java.util.ArrayList;
import java.util.List;

import cucumber.api.DataTable;
import cucumber.runtime.table.TableConverter;

public class DataTableDTO {

    public static class DataTableRowProxy {

        private List<String> cells;
        private List<Comment> comments;
        private Integer line;
        private DiffType diffType;

        public DataTableRowProxy() {
        }

        public DataTableRowProxy(DataTableRow row) {
            cells = row.getCells();
            comments = row.getComments();
            line = row.getLine();
            diffType = row.getDiffType();
        }

        public List<String> getCells() {
            return cells;
        }

        public void setCells(List<String> cells) {
            this.cells = cells;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
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
            return new DataTableRow(comments, cells, line, diffType);
        }
    }

    private List<DataTableRowProxy> rows = new ArrayList<DataTableRowProxy>();

    public DataTableDTO() {
    }

    public DataTableDTO(DataTable dataTable) {
        List<DataTableRow> gherkinRows = dataTable.getGherkinRows();
        for (DataTableRow gherkinRow : gherkinRows) {
            rows.add(new DataTableRowProxy(gherkinRow));
        }
    }

    public List<DataTableRowProxy> getRows() {
        return rows;
    }

    public void setRows(List<DataTableRowProxy> rows) {
        this.rows = rows;
    }

    public DataTable toDataTable(TableConverter tableConverter) {
        List<DataTableRow> gherkinRows = new ArrayList<DataTableRow>();
        for (DataTableRowProxy row : rows) {
            gherkinRows.add(row.toDataTableRow());
        }
        return new DataTable(gherkinRows, tableConverter);
    }
}
