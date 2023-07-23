package com.cjw.kafakproducer.controller;

import com.cjw.kafakproducer.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kafka")
public class KafkaProducerController {
    private final KafkaProducerService kafkaProducerService;

    @GetMapping("")
    public ResponseEntity<String> sendTest(@RequestParam String msg) {
        return ResponseEntity.ok(kafkaProducerService.sendMessage(msg));
    }
}
