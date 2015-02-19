package minium.cucumber.rest.dto;

import java.io.Serializable;
import java.util.UUID;

import cucumber.runtime.HookDefinition;

public class HookDefinitionDTO implements Serializable {

    private static final long serialVersionUID = 116997860196255407L;

    private UUID glueId;
    private long id;
    private int order;
    private String location;
    private String detailedLocation;

    public HookDefinitionDTO() {
    }

    public HookDefinitionDTO(UUID glueId, long id, HookDefinition hookDefinition) {
        this.glueId = glueId;
        this.id = id;
        this.order = hookDefinition.getOrder();
        this.location = hookDefinition.getLocation(false);
        this.detailedLocation = hookDefinition.getLocation(true);
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HookDefinitionDTO) {
            HookDefinitionDTO other = (HookDefinitionDTO) obj;
            return id == other.id;
        }
        return false;
    }
}
