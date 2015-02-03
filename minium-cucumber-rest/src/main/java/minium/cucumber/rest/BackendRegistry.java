package minium.cucumber.rest;

import java.util.HashMap;
import java.util.Map;

import cucumber.runtime.Backend;

public class BackendRegistry {

    private Map<String, Backend> backends = new HashMap<String, Backend>();

    public BackendRegistry register(String name, Backend backend) {
        Preconditions.checkState(!backends.containsKey(name), "Backend with name %s already registered", name);
        backends.put(name, backend);
        return this;
    }

    public Map<String, Backend> getAll() {
        return new HashMap<String, Backend>(backends);
    }

}
