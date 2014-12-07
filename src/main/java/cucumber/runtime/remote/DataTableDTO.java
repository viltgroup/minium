package cucumber.runtime.remote;

import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.Row.DiffType;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import cucumber.api.DataTable;
import cucumber.runtime.table.TableConverter;

@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class DataTableDTO {

    @JsonAutoDetect(
            getterVisibility = JsonAutoDetect.Visibility.NONE,
            setterVisibility = JsonAutoDetect.Visibility.NONE,
            fieldVisibility = JsonAutoDetect.Visibility.ANY
    )
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

    public DataTable toDataTable(TableConverter tableConverter) {
        List<DataTableRow> gherkinRows = new ArrayList<DataTableRow>();
        for (DataTableRowProxy row : rows) {
            gherkinRows.add(row.toDataTableRow());
        }
        return new DataTable(gherkinRows, tableConverter);
    }
}
