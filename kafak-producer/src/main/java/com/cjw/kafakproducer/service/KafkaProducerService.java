package com.cjw.kafakproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
    public static final String TOPIC = "spring-kafka";
    public static final String NOT_COMMIT_TEST_TOPIC = "not-commit-test";
    public static final String CONCURRENCY_TEST_TOPIC = "concurrency-test";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(String message) {
        printSendResult(kafkaTemplate.send(TOPIC, message));
        return "OK";
    }

    /**
     * 카프카는 키를 지정시 해당 키는 무조건 같은 파티션 번호로만 전송
     * -> 순서 보장
     */
    public String sendMessageWithKey(String key, String message) {
        printSendResult(kafkaTemplate.send(TOPIC, key, message));
        return "OK";
    }

    /**
     * 파티션 번호를 지정시 해당 키가 이전에 해시값을 통한 파티션으로 들어가도 무시
     */
    public String sendMessageWithKeyAndPartition(String key, String message) {
        printSendResult(kafkaTemplate.send(TOPIC, 0, key, message));
        return "OK";
    }

    /**
     * 컨슈머가 커밋 하지 않는 토픽 전송
     */
    public String sendMessageNotCommitTest(String message) {
        printSendResult(kafkaTemplate.send(NOT_COMMIT_TEST_TOPIC, message));
        return "NOT COMMIT TEST OK";
    }

    /**
     * 컨슈머가 커밋 하지 않는 토픽 전송
     */
    public String sendMessageConcurrencyTest(String message) {
        printSendResult(kafkaTemplate.send(CONCURRENCY_TEST_TOPIC, message));
        return "CONCURRENCY TEST OK";
    }

    /**
     * 전송 결과 로그 출력
     * recordMetadata=spring-kafka-1@2 -> 토픽명-파티션@오프셋
     * acks=all 인경우 성고 여부를 받지 않으므로 오프셋이 -1로 표기
     */
    private void printSendResult(ListenableFuture<SendResult<String, String>> result) {
        try {
            log.info(result.get().toString());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
