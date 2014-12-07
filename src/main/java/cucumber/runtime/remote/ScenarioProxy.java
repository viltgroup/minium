package cucumber.runtime.remote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import cucumber.api.Scenario;

@JsonAutoDetect(
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        setterVisibility = JsonAutoDetect.Visibility.NONE,
        fieldVisibility = JsonAutoDetect.Visibility.ANY
)
public class ScenarioProxy implements Scenario {

    public static class Data {

        private byte[] data;
        private String mimeType;

        public Data(byte[] data, String mimeType) {
            this.data = data;
            this.mimeType = mimeType;
        }

        public byte[] getData() {
            return data;
        }

        public void setData(byte[] data) {
            this.data = data;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }
    }

    private String id;
    private String name;
    private List<String> sourceTagNames;
    private String status;
    private boolean failed;
    private List<Data> embedded = new ArrayList<ScenarioProxy.Data>();
    private List<String> texts = new ArrayList<String>();

    public ScenarioProxy() {
    }

    public ScenarioProxy(Scenario scenario) {
        status = scenario.getStatus();
        sourceTagNames = new ArrayList<String>(scenario.getSourceTagNames());
        failed = scenario.isFailed();
        name = scenario.getName();
    }

    @Override
    public Collection<String> getSourceTagNames() {
        return sourceTagNames;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public void embed(byte[] data, String mimeType) {
        embedded.add(new Data(data, mimeType));
    }

    @Override
    public void write(String text) {
        texts.add(text);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    public void populate(Scenario scenario) {
        for (Data data : embedded) {
            scenario.embed(data.data, data.mimeType);
        }
        for (String text : texts) {
            scenario.write(text);
        }
    }
}
