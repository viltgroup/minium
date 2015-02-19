package minium.cucumber.rest.dto;

import gherkin.formatter.Argument;

import java.util.List;

public class StepMatchDTO {

    private ArgumentDTO[] arguments;

    public StepMatchDTO() {
    }

    public StepMatchDTO(List<Argument> matchedArguments) {
        this.arguments = convertArguments(matchedArguments);
    }

    public ArgumentDTO[] getArguments() {
        return arguments;
    }

    public void setArguments(ArgumentDTO[] arguments) {
        this.arguments = arguments;
    }

    public ArgumentDTO[] convertArguments(List<Argument> args) {
        if (args == null) return null;
        ArgumentDTO[] dtos = new ArgumentDTO[args.size()];
        for (int i = 0; i < args.size(); i++) {
            Argument arg = args.get(i);
            dtos[i] = new ArgumentDTO(arg);
        }
        return dtos;
    }
}
