package com.fernandoog;

import com.fernandoog.controller.ScriptController;
import com.fernandoog.model.Script;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(ScriptController.class)
class ScriptIotCoreTests {

    @MockBean
    private ScriptController scriptController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    void createScript() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/scripts").content("{\"Script\":{\"code\":\"ping\"}}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteScript() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/scripts/1")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getAllScripts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/scripts")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void getScriptById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/scripts/1")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    void updateScript() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/scripts/1").content("{\"Script\":{\"code\":\"ping\"}}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void launchScriptById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/scripts/exec/1")
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
