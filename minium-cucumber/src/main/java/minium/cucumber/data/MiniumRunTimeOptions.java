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
package minium.cucumber.data;

import java.io.File;
import java.util.List;

import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.Shellwords;
import cucumber.runtime.formatter.PluginFactory;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.model.CucumberFeature;

public class MiniumRunTimeOptions extends RuntimeOptions {

    private File resourceDir;

    public MiniumRunTimeOptions(List<String> argv) {
        super(argv);
    }

    public MiniumRunTimeOptions(String argv) {
        super(new PluginFactory(), Shellwords.parse(argv));
    }

    public MiniumRunTimeOptions(List<String> argv, File resourceDir) {
        super(argv);
        this.resourceDir = resourceDir;
    }

    @Override
    public List<CucumberFeature> cucumberFeatures(ResourceLoader resourceLoader) {
        return MiniumFeatureBuilder.load(resourceLoader, this.getFeaturePaths(), this.getFilters(), System.out,resourceDir);
    }

    public List<CucumberFeature> cucumberFeatures(ResourceLoader resourceLoader,boolean x) {
        return MiniumFeatureBuilder.load(resourceLoader, this.getFeaturePaths(), this.getFilters(), System.out,resourceDir);
    }

}
