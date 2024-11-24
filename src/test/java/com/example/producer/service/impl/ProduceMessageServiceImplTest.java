package com.example.producer.service.impl;

import com.example.producer.config.RabbitMQConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class ProduceMessageServiceImplTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ProduceMessageServiceImpl produceMessageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Produce message in queue")
    void produceMessageInQueue() {
        String message = "Test message";
        String expectedResponse = "Message(Test message) has been produced.";

        String actualResponse = produceMessageService.produceMessage(message);

        assertEquals(expectedResponse, actualResponse);
        verify(rabbitTemplate).convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "myRoutingKey.messages", message);
    }
}