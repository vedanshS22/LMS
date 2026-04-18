package com.example.learnermanagementsystem.Controller;

import com.example.learnermanagementsystem.Service.LearnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LearnerController.class)
class LearnerControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LearnerService learnerService;

    @Test
    void createLearner_invalidEmail_returns400WithFieldMessage() throws Exception {
        String body = """
                {
                  "learnerName": "Test User",
                  "learnerEmail": "not-valid",
                  "learnerPhone": "12345678"
                }
                """;
        mockMvc.perform(post("/api/v1/learners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.fieldErrors.learnerEmail").exists());
    }

    @Test
    void createLearner_blankName_returns400() throws Exception {
        String body = """
                {
                  "learnerName": "",
                  "learnerEmail": "a@b.com",
                  "learnerPhone": "12345678"
                }
                """;
        mockMvc.perform(post("/api/v1/learners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.learnerName").exists());
    }
}
