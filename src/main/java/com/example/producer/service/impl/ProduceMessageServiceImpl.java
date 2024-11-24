package com.example.producer.service.impl;

import com.example.producer.config.RabbitMQConfig;
import com.example.producer.service.ProduceMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProduceMessageServiceImpl implements ProduceMessageService {

    private final RabbitTemplate rabbitTemplate;

    public String produceMessage(String message) {
        log.info("Producing message: {}", message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "myRoutingKey.messages",
                message);
        return "Message(" + message + ")" + " has been produced.";
    }
}