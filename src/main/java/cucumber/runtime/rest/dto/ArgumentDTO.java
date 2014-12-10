package cucumber.runtime.rest.dto;

import gherkin.formatter.Argument;

public class ArgumentDTO {

    private Integer offset;
    private String val;

    public ArgumentDTO() {
    }

    public ArgumentDTO(Argument argument) {
        this.offset = argument.getOffset();
        this.val = argument.getVal();
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Argument toArgument() {
        return new Argument(offset, val);
    }
}