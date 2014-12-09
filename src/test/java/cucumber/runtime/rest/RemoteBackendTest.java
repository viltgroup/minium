package cucumber.runtime.rest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cucumber.runtime.Backend;
import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.java.JavaBackend;
import cucumber.runtime.java.ObjectFactory;
import cucumber.runtime.model.CucumberFeature;
import cucumber.runtime.rest.RemoteBackend;
import cucumber.runtime.rest.RemoteBackendTest.App;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
public class RemoteBackendTest {

    private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @EnableAutoConfiguration
    @Import({ TestRestWebConfig.class, TestConfig.class })
    public static class App {
    }

    @Configuration
    public static class TestConfig {

        @Bean
        public ObjectFactory objectFactory() {
            return JavaBackend.loadObjectFactory(classFinder());
        }

        @Bean
        public ClassFinder classFinder() {
            return new ResourceLoaderClassFinder(resourceLoader(), classLoader);
        }

        @Bean
        public ResourceLoader resourceLoader() {
            return new MultiLoader(classLoader);
        }

        @Bean
        public Backend backend() {
            return new JavaBackend(objectFactory());
        }
    }

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ClassFinder classFinder;

    @Test
    public void testLoadGlue() throws Throwable {
        RemoteBackend remoteBackend = new RemoteBackend("http://localhost:8080/cucumber");

        final CucumberFeature feature = TestHelper.feature("nice.feature", join(
                "Feature: Be nice",
                "",
                "Scenario: Say hello to Cucumber",
                "When I say hello to Cucumber",
                "And I say hello to:",
                "| name | gender |",
                "| John | MALE   |",
                "| Mary | FEMALE |"));

        RuntimeOptions runtimeOptions = new RuntimeOptions(Arrays.asList(
                "--glue", "cucumber.runtime.remote.stepdefs",
                "--plugin", "pretty",
                "--monochrome"
                )) {
            @Override
            public List<CucumberFeature> cucumberFeatures(ResourceLoader resourceLoader) {
                return Collections.singletonList(feature);
            }
        };

        Runtime runtime = new Runtime(resourceLoader, classLoader, Collections.singletonList(remoteBackend), runtimeOptions);
        runtime.run();
    }

    private String join(String ... lines) {
        if (lines == null || lines.length == 0) return "";
        StringBuilder buf = new StringBuilder();
        for (String line : lines) {
            buf.append(line);
            buf.append("\n");
        }
        return buf.substring(0, buf.length() - 1);
    }
}
