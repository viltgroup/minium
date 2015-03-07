/*
 * Copyright (C) 2015 The Minium Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package minium.cucumber.rest;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;

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
