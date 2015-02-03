package minium.cucumber.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Throwables;

public class ConfigProperties extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = -1491942616465352922L;

    public ConfigProperties() {
    }

    public ConfigProperties(Map<String, Object> props) {
        super(props);
    }


    public String toJson() {
        try {
            return toJsonObject().toString();
        } catch (JSONException e) {
            throw Throwables.propagate(e);
        }
    }

    protected Object toJsonObject() throws JSONException {
        if (isArray()) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < keySet().size(); i++) {
                Object elem = convert(get(Integer.toString(i)));
                array.put(elem);
            }
            return array;
        } else {
            JSONObject obj = new JSONObject();
            for (String prop : keySet()) {
                Object elem = convert(get(prop));
                obj.put(prop, elem);
            }
            return obj;
        }
    }

    @SuppressWarnings("unchecked")
    private Object convert(Object object) throws JSONException {
        if (object == null) return null;
        if (!(object instanceof Map)) return object;
        return new ConfigProperties((Map<String, Object>) object).toJsonObject();
    }

    private boolean isArray() {
        for (int i = 0; i < keySet().size(); i++) {
            if (!keySet().contains(Integer.toString(i))) return false;
        }
        return true;
    }
}
