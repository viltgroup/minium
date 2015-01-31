/*
 * Copyright (C) 2013 The Minium Authors
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
package minium.script.rhinojs;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.google.common.collect.Lists;

@ConfigurationProperties(prefix = "minium.script.rhino")
public class RhinoProperties {

    public static class RequireProperties {

        private List<String> modulePaths = Lists.newArrayList("classpath*:modules");
        private boolean sandboxed;

        public List<String> getModulePaths() {
            return modulePaths;
        }

        public boolean isSandboxed() {
            return sandboxed;
        }

        public void setSandboxed(boolean sandboxed) {
            this.sandboxed = sandboxed;
        }
    }

    private RequireProperties require;

    public RequireProperties getRequire() {
        return require;
    }

    public void setRequire(RequireProperties require) {
        this.require = require;
    }

}
