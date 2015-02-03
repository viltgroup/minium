package cucumber.runtime.rest.dto;

import gherkin.formatter.model.Tag;

public class TagDTO {

    private String name;
    private Integer line;

    public TagDTO() {
    }

    public TagDTO(Tag tag) {
        name = tag.getName();
        line = tag.getLine();
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

    public Tag toTag() {
        return new Tag(name, line);
    }
}
