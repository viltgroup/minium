package minium.cucumber.rest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import minium.cucumber.rest.BackendConfigurer;
import minium.cucumber.rest.BackendRegistry;
import minium.cucumber.rest.RemoteBackend;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

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

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@SpringApplicationConfiguration(classes = { CucumberRestConfiguration.class, TestConfig.class })
//@IntegrationTest
public class RemoteBackendTest {

    private static final String REST_BASE_URL = "http://localhost:8080/cucumber/backends/b1";

    private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @Configuration
    public static class TestConfig implements BackendConfigurer {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

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

        @Override
        public void addBackends(BackendRegistry registry) {
            registry.register("b1", new JavaBackend(objectFactory()));
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ClassFinder classFinder;

    @SuppressWarnings("unused")
    private MockRestServiceServer mockServer;

    @Before
    public void setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

//    @Test
    public void testLoadGlue() throws Throwable {
        RemoteBackend remoteBackend = new RemoteBackend(REST_BASE_URL, restTemplate);
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
