package cucumber.runtime.remote;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import cucumber.runtime.ClassFinder;
import cucumber.runtime.Runtime;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.io.MultiLoader;
import cucumber.runtime.io.ResourceLoader;
import cucumber.runtime.io.ResourceLoaderClassFinder;
import cucumber.runtime.model.CucumberFeature;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MpayBackendTest.TestConfig.class)
public class MpayBackendTest {

    private static ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    @Configuration
    public static class TestConfig {

        @Bean
        public ClassFinder classFinder() {
            return new ResourceLoaderClassFinder(resourceLoader(), classLoader);
        }

        @Bean
        public ResourceLoader resourceLoader() {
            return new MultiLoader(classLoader);
        }
    }

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ClassFinder classFinder;

    @Test
    public void testLoadGlue() throws Throwable {
         Credentials credentials = new UsernamePasswordCredentials("admin", "liberty09!");

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials( AuthScope.ANY, credentials);

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credsProvider).build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        //3. create restTemplate
        RestTemplate restTemplate = new RestTemplate(factory);

        RemoteBackend remoteBackend = new RemoteBackend("http://localhost:8080/cucumber-backend", restTemplate );

        final CucumberFeature feature = TestHelper.feature("mpay.feature", join(
                "Feature: Test given steps",
                "",
                "Scenario: Test given steps",
                "Given the following merchants exist:",
                "| name       | code      | phone     |",
                "| Background | 123456789 | 912345678 |",
                "And the following stores exist:",
                "| name              | fiscalId  | merchantName | active |",
                "| StoreTest         | 123456789 | Background   | true   |",
                "| InactiveStoreTest | 123456790 | Background   | false  |",
                "And the following operators exist:",
                "| username | cgdId    | pinCode | merchantName | operatorType  |",
                "| admin    | 12345678 | 12345   | Background   | Administrator |"));

        RuntimeOptions runtimeOptions = new RuntimeOptions(Arrays.asList(
                "--glue", "com.vilt.mpay.e2e.service",
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
