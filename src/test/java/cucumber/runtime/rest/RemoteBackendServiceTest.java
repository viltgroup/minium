package cucumber.runtime.rest;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cucumber.runtime.Backend;
import cucumber.runtime.rest.CucumberRestController;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MockRestConfig.class, TestRestWebConfig.class })
public class RemoteBackendServiceTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    @Qualifier("backendMock")
    private Backend backend;

    @Autowired
    private CucumberRestController backendService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .alwaysDo(print())
                .build();
        reset(backend);
    }

//    @Test
//    public void testCreateWorld() throws Exception {
//        // when
//        this.mockMvc
//                .perform(post("/worlds").accept(MediaType.APPLICATION_JSON))
//
//        // then
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.uuid").exists());
//        verify(backend).buildWorld();
//        assertThat(backendService.world, notNullValue());
//    }
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
