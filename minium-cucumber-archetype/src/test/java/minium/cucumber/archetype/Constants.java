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
package minium.cucumber.archetype;

import java.util.Properties;

public class Constants {

    // GAV for the archetype
    public static final String ARCHETYPE_GROUP_ID = "minium";
    public static final String ARCHETYPE_ARTEFACT_ID = "minium-cucumber-archetype";
    public static final String ARCHETYPE_VERSION = System.getProperty("project.version");

    // GAV for the created artefact from the archetype which we want to test
    public static final String TEST_GROUP_ID = "my.archetype";
    public static final String TEST_ARTIFACT_ID = "my-archetype-test";
    public static final String TEST_VERSION = "1.0-SNAPSHOT";
    public static final String TEST_FEATURE = "test_my_archetype";
    public static final String TEST_CLASS_NAME = "MyArchetypeIT";

    public static Properties getSystemProperties() {
        Properties props = new Properties(System.getProperties());
        props.put("archetypeGroupId", Constants.ARCHETYPE_GROUP_ID);
        props.put("archetypeArtifactId", Constants.ARCHETYPE_ARTEFACT_ID);
        props.put("archetypeVersion", Constants.ARCHETYPE_VERSION);
        props.put("groupId", Constants.TEST_GROUP_ID);
        props.put("artifactId", Constants.TEST_ARTIFACT_ID);
        props.put("version", Constants.TEST_VERSION);
        props.put("feature", Constants.TEST_FEATURE);
        props.put("testClassname", Constants.TEST_CLASS_NAME);
        props.put("interactiveMode", "false");

        return props;
    }
}