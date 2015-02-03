package minium.cucumber.rest;

import java.util.Collection;

import minium.cucumber.rest.dto.ScenarioDTO;
import minium.cucumber.rest.dto.ScenarioDTO.Data;
import cucumber.api.Scenario;

public class ScenarioAdapter implements Scenario {

    private ScenarioDTO scenarioDto;

    public ScenarioAdapter(ScenarioDTO scenarioDto) {
        this.scenarioDto = scenarioDto;
    }

    @Override
    public Collection<String> getSourceTagNames() {
        return scenarioDto.getSourceTagNames();
    }

    @Override
    public String getStatus() {
        return scenarioDto.getStatus();
    }

    @Override
    public boolean isFailed() {
        return scenarioDto.isFailed();
    }

    @Override
    public void embed(byte[] data, String mimeType) {
        scenarioDto.getEmbedded().add(new Data(data, mimeType));
    }

    @Override
    public void write(String text) {
        scenarioDto.getTexts().add(text);
    }

    @Override
    public String getName() {
        return scenarioDto.getName();
    }

    @Override
    public String getId() {
        return scenarioDto.getId();
    }

}
