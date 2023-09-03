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

    @GetMapping(value = "", name = "메시지 전송")
    public ResponseEntity<String> sendTest(@RequestParam String msg) {
        return ResponseEntity.ok(kafkaProducerService.sendMessage(msg));
    }

    @GetMapping(value = "/with-key", name = "Key 포함 메시지 전송")
    public ResponseEntity<String> sendTestWithKey(@RequestParam String key, @RequestParam String msg) {
        return ResponseEntity.ok(kafkaProducerService.sendMessageWithKey(key, msg));
    }

    @GetMapping(value = "/partition", name = "파티션 지정 전송")
    public ResponseEntity<String> sendTestPartition(@RequestParam String key, @RequestParam String msg) {
        return ResponseEntity.ok(kafkaProducerService.sendMessageWithKeyAndPartition(key, msg));
    }

    @GetMapping(value = "/not-commit", name = "컨슈머 커밋 X 테스트 토픽 전송")
    public ResponseEntity<String> sendTestForNotCommit(@RequestParam String msg) {
        return ResponseEntity.ok(kafkaProducerService.sendMessageNotCommitTest(msg));
    }

    @GetMapping(value = "/concurrency-test", name = "컨슈머 커밋 X 테스트 토픽 전송")
    public ResponseEntity<String> sendTestForConcurrency(@RequestParam String msg) {
        return ResponseEntity.ok(kafkaProducerService.sendMessageConcurrencyTest(msg));
    }
}
