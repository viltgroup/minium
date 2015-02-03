package cucumber.runtime.rest.dto;

import gherkin.formatter.model.DataTableRow;
import gherkin.formatter.model.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cucumber.runtime.rest.dto.DataTableDTO.DataTableRowDTO;

public class StepDTO implements Serializable {

    private static final long serialVersionUID = 578395699182857236L;

    private List<CommentDTO> comments;
    private String keyword;
    private String name;
    private Integer line;
    private List<DataTableRowDTO> rows;
    private DocStringDTO docString;

    public StepDTO() {
    }

    public StepDTO(Step step) {
        this.comments = CommentDTO.fromGherkinComments(step.getComments());
        this.keyword = step.getKeyword();
        this.name = step.getName();
        this.line = step.getLine();
        if (step.getRows() != null) {
            this.rows = new ArrayList<DataTableRowDTO>();
            for (DataTableRow gherkinRow : step.getRows()) {
                this.rows.add(new DataTableRowDTO(gherkinRow));
            }
        }
        if (step.getDocString() != null) {
            this.docString = new DocStringDTO(step.getDocString());
        }
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
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

    public List<DataTableRowDTO> getRows() {
        return rows;
    }

    public void setRows(List<DataTableRowDTO> rows) {
        this.rows = rows;
    }

    public DocStringDTO getDocString() {
        return docString;
    }

    public void setDocString(DocStringDTO docString) {
        this.docString = docString;
    }

    public Step toStep() {
        return new Step(
                CommentDTO.toGherkinComments(comments),
                keyword,
                name,
                line,
                toGherkingRows(),
                docString == null ? null : docString.toDocString());
    }

    private List<DataTableRow> toGherkingRows() {
        if (rows == null) return null;
        List<DataTableRow> gherkinRows = new ArrayList<DataTableRow>();
        for (DataTableRowDTO row : rows) {
            gherkinRows.add(row.toDataTableRow());
        }
        return gherkinRows;
    }
}
