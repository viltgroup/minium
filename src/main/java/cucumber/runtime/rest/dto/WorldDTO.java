package cucumber.runtime.rest.dto;

import java.io.Serializable;
import java.util.UUID;

public class WorldDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID uuid;
    private String backendName;

    public WorldDTO() {
    }

    public WorldDTO(UUID uuid, String backendName) {
        this.uuid = uuid;
        this.backendName = backendName;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getBackendName() {
        return backendName;
    }

    public void setBackendName(String backendName) {
        this.backendName = backendName;
    }

    @Override
    public int hashCode() {
        return uuid == null ? System.identityHashCode(this) : uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GlueDTO && uuid != null) {
            return uuid.equals(((GlueDTO) obj).getUuid());
        }
        return false;
    }
}
