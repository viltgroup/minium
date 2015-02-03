package minium.cucumber.rest;

import minium.cucumber.rest.dto.StepDTO;
import cucumber.api.SnippetType;

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
