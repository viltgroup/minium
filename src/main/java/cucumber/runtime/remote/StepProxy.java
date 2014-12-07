package cucumber.runtime.remote;

import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.DocString;
import gherkin.formatter.model.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import cucumber.runtime.remote.DataTableProxy.DataTableRowProxy;

@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class StepProxy implements Serializable {

    private static final long serialVersionUID = 578395699182857236L;

    private List<Comment> comments;
    private String keyword;
    private String name;
    private Integer line;
    private List<DataTableRowProxy> rows;
    private DocString docString;

    public StepProxy() {
    }

    public StepProxy(Step step) {
        this.comments = step.getComments();
        this.keyword = step.getKeyword();
        this.name = step.getName();
        this.line = step.getLine();
        if (step.getRows() != null) {
            this.rows = new ArrayList<DataTableRowProxy>();
            for (DataTableRow gherkinRow : step.getRows()) {
                this.rows.add(new DataTableRowProxy(gherkinRow));
            }
        }
        this.docString = step.getDocString();
    }

    public Step toStep() {
        return new Step(
                comments,
                keyword,
                name,
                line,
                toGherkingRows(),
                docString);
    }

    private List<DataTableRow> toGherkingRows() {
        if (rows == null) return null;
        List<DataTableRow> gherkinRows = new ArrayList<DataTableRow>();
        for (DataTableRowProxy row : rows) {
            gherkinRows.add(row.toDataTableRow());
        }
        return gherkinRows;
    }
}
