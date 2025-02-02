package com.torresj.infosas.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.torresj.infosas.dtos.QueueMessage;
import com.torresj.infosas.services.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitmqProducerService implements ProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(QueueMessage message) {
        try {
            rabbitTemplate.convertAndSend(
                    "notificationsExchange",
                    message.getType().name(),
                    objectMapper.writeValueAsString(message)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
