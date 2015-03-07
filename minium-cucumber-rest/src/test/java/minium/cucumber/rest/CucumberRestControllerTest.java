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
package minium.cucumber.rest;

import static minium.cucumber.rest.CucumberRestConstants.URL_PREFIX;
import static minium.cucumber.rest.CucumberRestConstants.WORLDS_URI;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cucumber.runtime.Backend;

//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = { MockRestConfig.class, CucumberRestConfiguration.class })
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

//    @Test
    public void testCreateWorld() throws Exception {
        // when
        this.mockMvc
                .perform(post(URL_PREFIX + WORLDS_URI, "mockedBackend"))

        // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.backendName").value("mockedBackend"))
                .andReturn();
        verify(mockedBackend).buildWorld();
        assertThat(controller.backendContexts.get("mockedBackend").worlds.entrySet(), hasSize(1));
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
