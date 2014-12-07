package cucumber.runtime.remote;

import gherkin.formatter.model.Tag;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import cucumber.api.Scenario;
import cucumber.runtime.HookDefinition;

@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class HookDefinitionProxy implements HookDefinition, Serializable {

    private static final long serialVersionUID = 116997860196255407L;

    private UUID glueId;
    private long id;
    private transient RemoteBackend remoteBackend;

    public HookDefinitionProxy() {
    }

    public HookDefinitionProxy(UUID glueId, long id) {
        this.glueId = glueId;
        this.id = id;
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

    public void setRemoteBackend(RemoteBackend remoteBackend) {
        this.remoteBackend = remoteBackend;
    }

    @Override
    public void execute(Scenario scenario) throws Throwable {
        remoteBackend.execute(this, scenario);
    }

    @Override
    public String getLocation(boolean detail) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getOrder() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean matches(Collection<Tag> tags) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HookDefinitionProxy) {
            HookDefinitionProxy other = (HookDefinitionProxy) obj;
            return id == other.id;
        }
        return false;
    }
}
