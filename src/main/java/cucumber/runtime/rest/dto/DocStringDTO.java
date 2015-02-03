package cucumber.runtime.rest.dto;

import gherkin.formatter.model.DocString;

public class DocStringDTO {

    private String contentType;
    private String value;
    private int line;

    public DocStringDTO() {
    }

    public DocStringDTO(DocString doc) {
        contentType = doc.getContentType();
        line = doc.getLine();
        value = doc.getValue();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public int getLine() {
        return line;
    }



    public void setLine(int line) {
        this.line = line;
    }



    public DocString toDocString() {
        return new DocString(contentType, value, line);
    }

}
