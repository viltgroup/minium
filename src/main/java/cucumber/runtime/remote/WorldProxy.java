package cucumber.runtime.remote;

import java.io.Serializable;
import java.util.UUID;

public class WorldProxy implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID uuid;

    public WorldProxy() {
    }

    public WorldProxy(UUID uuid) {
        this.uuid = uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public int hashCode() {
        return uuid == null ? System.identityHashCode(this) : uuid.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GlueProxy && uuid != null) {
            return uuid.equals(((GlueProxy) obj).getUuid());
        }
        return false;
    }
}
