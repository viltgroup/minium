package cucumber.runtime.rest.dto;

import gherkin.formatter.model.Comment;
import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.DocString;
import gherkin.formatter.model.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cucumber.runtime.rest.dto.DataTableDTO.DataTableRowProxy;

public class StepDTO implements Serializable {

    private static final long serialVersionUID = 578395699182857236L;

    private List<Comment> comments;
    private String keyword;
    private String name;
    private Integer line;
    private List<DataTableRowProxy> rows;
    private DocString docString;

    public StepDTO() {
    }

    public StepDTO(Step step) {
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public List<DataTableRowProxy> getRows() {
        return rows;
    }

    public void setRows(List<DataTableRowProxy> rows) {
        this.rows = rows;
    }

    public DocString getDocString() {
        return docString;
    }

    public void setDocString(DocString docString) {
        this.docString = docString;
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
