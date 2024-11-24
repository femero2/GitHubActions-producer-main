package com.example.producer.controller;

import com.example.producer.service.ProduceMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProduceMessageController.class)
class ProduceMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProduceMessageService produceMessageService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Produce message in queue")
    void produceMessageInQueue() throws Exception {
        String message = "Test message";
        String expectedResponse = "Message produced: Test message";

        when(produceMessageService.produceMessage(message)).thenReturn(expectedResponse);

        mockMvc.perform(post("/produce")
                .param("message", message))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}