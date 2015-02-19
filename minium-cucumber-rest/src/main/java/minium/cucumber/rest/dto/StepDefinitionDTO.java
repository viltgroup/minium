package minium.cucumber.rest.dto;

import java.io.Serializable;
import java.util.UUID;

import cucumber.runtime.StepDefinition;

public class StepDefinitionDTO implements Serializable {

    private static final long serialVersionUID = 1195844972068596887L;

    private UUID glueId;
    private long id;
    private String pattern;
    private Integer parameterCount;
    private String location;
    private String detailedLocation;

    public StepDefinitionDTO() {
    }

    public StepDefinitionDTO(UUID glueId, long id, StepDefinition stepDefinition) {
        this.glueId = glueId;
        this.id = id;
        this.pattern = stepDefinition.getPattern();
        this.parameterCount = stepDefinition.getParameterCount();
        this.location = stepDefinition.getLocation(false);
        this.detailedLocation = stepDefinition.getLocation(true);
    }

    public UUID getGlueId() {
        return glueId;
    }

    public void setGlueId(UUID glueId) {
        this.glueId = glueId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Integer getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(Integer parameterCount) {
        this.parameterCount = parameterCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetailedLocation() {
        return detailedLocation;
    }

    public void setDetailedLocation(String detailedLocation) {
        this.detailedLocation = detailedLocation;
    }

}

