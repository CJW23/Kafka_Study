package com.cjw.kafakproducer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(String userMessage) {
        kafkaTemplate.send("spring-kafka", userMessage);
        return "OK";
    }
}
