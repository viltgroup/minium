package cucumber.runtime.remote;

import gherkin.formatter.model.DocString;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class DocStringProxy {

    private String contentType;
    private String value;
    private int line;

    public DocStringProxy(DocString doc) {
        contentType = doc.getContentType();
        line = doc.getLine();
        value = doc.getValue();
    }

    public DocString toDocString() {
        return new DocString(contentType, value, line);
    }

    public String getValue() {
        return value;
    }

}
