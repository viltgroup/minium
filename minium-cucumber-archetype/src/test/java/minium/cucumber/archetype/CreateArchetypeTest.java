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

import static java.lang.String.format;

import java.io.File;
import java.io.IOException;

import org.apache.maven.it.VerificationException;
import org.apache.maven.it.Verifier;
import org.junit.Before;
import org.junit.Test;

public class CreateArchetypeTest {

    public static final File ROOT = new File("target/test-classes/");

    @Before
    public void setUp() throws VerificationException, IOException {
        Verifier verifier;

        /*
         * We must first make sure that any artifact created
         * by this test has been removed from the local
         * repository. Failing to do this could cause
         * unstable test results. Fortunately, the verifier
         * makes it easy to do this.
         */
        verifier = new Verifier(ROOT.getAbsolutePath());

        // Deleting a former created artifact from the archetype to be tested
        verifier.deleteArtifact(Constants.TEST_GROUP_ID, Constants.TEST_ARTIFACT_ID, Constants.TEST_VERSION, null);

        // Delete the created maven project
        verifier.deleteDirectory(Constants.TEST_ARTIFACT_ID);
    }

    @Test
    public void testGenerateArchetype() throws VerificationException {
        Verifier verifier = new Verifier(ROOT.getAbsolutePath());
        verifier.setSystemProperties(Constants.getSystemProperties());
        verifier.setAutoclean(false);

        verifier.executeGoal("archetype:generate");

        verifier.verifyErrorFreeLog();
    }

    @Test
    public void testGenerateAndBuildArchetypeArtefact() throws VerificationException {
        Verifier verifier = new Verifier(ROOT.getAbsolutePath());
        verifier.setSystemProperties(Constants.getSystemProperties());
        verifier.setAutoclean(false);

        verifier.executeGoal("archetype:generate");

        verifier.verifyErrorFreeLog();

        // Since creating the archetype was successful, we now want to actually build the generated project
        verifier = new Verifier(ROOT.getAbsolutePath() + "/" + Constants.TEST_ARTIFACT_ID);
        verifier.setAutoclean(true);
        verifier.executeGoal("verify");

        verifier.verifyErrorFreeLog();

        // verify output
        verifier.verifyTextInLog(format("Running %s.%s", Constants.TEST_GROUP_ID, Constants.TEST_CLASS_NAME));
        verifier.verifyTextInLog("Tests run: 12, Failures: 0, Errors: 0, Skipped: 0");

        // ensure files exist
        verifier.assertFilePresent(format("src/test/resources/features/%s.feature", Constants.TEST_FEATURE));
        verifier.assertFilePresent(format("src/test/resources/steps/%s_stepdefs.js", Constants.TEST_FEATURE));
    }
}