package cucumber.runtime.rest;

import cucumber.api.SnippetType;
import cucumber.runtime.rest.dto.StepDTO;

public class SnippetRequestDTO {

    private StepDTO step;
    private SnippetType type;

    public SnippetRequestDTO() {
    }

    public SnippetRequestDTO(StepDTO step, SnippetType snippetType) {
        this.step = step;
        type = snippetType;
    }

    public StepDTO getStep() {
        return step;
    }

    public void setStep(StepDTO step) {
        this.step = step;
    }

    public SnippetType getType() {
        return type;
    }

    public void setType(SnippetType type) {
        this.type = type;
    }
}
