package cucumber.runtime.rest;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cucumber.runtime.Backend;

@Configuration
public class MockRestConfig implements BackendConfigurer {

    @Bean(name = "mockedBackend")
    public Backend backendMock() {
        return mock(Backend.class);
    }

    @Override
    public void addBackends(BackendRegistry registry) {
        registry.register("mockedBackend", backendMock());
    }
}
