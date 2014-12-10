package cucumber.runtime.rest;

import static cucumber.runtime.rest.CucumberRestConstants.CONTROLLER_PREFIX;
import static cucumber.runtime.rest.CucumberRestConstants.WORLDS_URI;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cucumber.runtime.Backend;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MockRestConfig.class, CucumberRestConfig.class })
public class CucumberRestControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private Backend mockedBackend;

    @Autowired
    private CucumberRestController controller;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .alwaysDo(print())
                .build();
        reset(mockedBackend);
    }

    @Test
    public void testCreateWorld() throws Exception {
        // when
        this.mockMvc
                .perform(post(CONTROLLER_PREFIX + WORLDS_URI, "mockedBackend"))

        // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.backendName").value("mockedBackend"));
        verify(mockedBackend).buildWorld();
        assertThat(controller.backendContexts.get("mockedBackend").world, notNullValue());
    }
//
//    @Test
//    public void testDeleteWorld() throws Exception {
//        // given
//        WorldDTO world = backendService.createWorld();
//
//        // when
//        this.mockMvc
//                .perform(delete("/worlds/{uuid}", world.getUuid().toString()))
//
//        // then
//                .andExpect(status().isOk());
//        verify(backend).disposeWorld();
//        assertThat(backendService.world, nullValue());
//    }
//
//    @Test
//    public void testCreateGlue() throws Exception {
//        // when
//        this.mockMvc
//                .perform(post("/glues").param("path", "foo.bar", "my.glue"))
//
//        // then
//                .andExpect(status().isCreated());
//        verify(backend).loadGlue(Mockito.any(Glue.class), Mockito.eq(Arrays.asList("foo.bar", "my.glue")));
//    }
//
//    @Test
//    public void testGetGlue() throws Exception {
//        // given
//        GlueProxy glue1 = backendService.createGlue("foo.bar");
//        GlueProxy glue2 = backendService.createGlue("my.glue");
//
//        glue1.addStepDefinition(mock(StepDefinition.class));
//        glue1.addAfterHook(mock(HookDefinition.class));
//        glue1.addAfterHook(mock(HookDefinition.class));
//        glue1.addBeforeHook(mock(HookDefinition.class));
//
//        // when
//        this.mockMvc
//                .perform(get("/glues"))
//
//        // then
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(2)));
//
//        verify(backend).loadGlue(glue1, Arrays.asList("foo.bar"));
//        verify(backend).loadGlue(glue2, Arrays.asList("my.glue"));
//    }


}
