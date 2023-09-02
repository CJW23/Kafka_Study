package com.cjw.kafkaconsumer.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TopicSpringConsumerListener {
    @KafkaListener(topics = "${spring.kafka.topic.spring-kafka-topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumerTest(ConsumerRecord<String, String> record) {
        String key = record.key();
        String value = record.value();
        String topic = record.topic();
        int partition = record.partition();
        long offset = record.offset();

        log.info("===============================");
        log.info("Received message:");
        log.info("Topic: " + topic);
        log.info("Partition: " + partition);
        log.info("Offset: " + offset);
        log.info("Key: " + key);
        log.info("Value: " + value);
        log.info("===============================");
    }

    /**
     * id 설정시 id -> group id로 설정
     */
    @KafkaListener(id = "id-test1", topics = "${spring.kafka.topic.spring-kafka-topic}")
    public void idTestConsume1(ConsumerRecord<String, String> record) {
        log.info("===========id-test1 Start=============");
        log.info("Topic: " + record.topic());
        log.info("Value: " + record.value());
        log.info("===========id-test1 End=============");
    }

    @KafkaListener(id = "id-test2", topics = "${spring.kafka.topic.spring-kafka-topic}")
    public void idTestConsume2(ConsumerRecord<String, String> record) {
        log.info("===========id-test2 Start=============");
        log.info("Topic: " + record.topic());
        log.info("Value: " + record.value());
        log.info("===========id-test2 End=============");
    }

    /**
     * commit 하지 않으면 브로커의 offset 업데이트 되지 않는다.
     * 하지만 kafkaConsumer 자체적으로 offset 관리하고 있기때문에 중간에 commit 되지 않아도(에러등으로 인해) 다음 offset 데이터를 계속 불러옴
     */
    @KafkaListener(topics = "not-commit-test", groupId = "${spring.kafka.consumer.group-id}")
    public void notCommitTestConsume(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        log.info("===========not-commit Start=============");
        log.info("Topic: " + record.topic());
        log.info("Value: " + record.value());
        log.info("Offset: " + record.offset());
        log.info("===========not-commit End=============");
        throw new RuntimeException("Error Test");
    }
}