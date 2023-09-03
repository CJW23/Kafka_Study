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
     * id 설정시 *idIsGroup* default true 값으로 인해 id -> group id로 설정
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

    /**
     * autoStartup: 리스너 엔드포인트 등록
     * 즉 autoStartup true: 해당 listener 동작 수행
     */
    @KafkaListener(autoStartup = "false", topics = "not-commit-test", groupId = "${spring.kafka.consumer.group-id}")
    public void autoStartup(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
    }


    /**
     * concurrency : partition = 1 : 1?
     * concurrency thread -> 파티션당 하나의 thread 할당
     * 컨슈머 그룹이 2개 인스턴스가 띄워져 있는 경우 각 컨슈머가 파티션 하나씩 할당
     * 만약 컨슈머 인스턴스가 2개, concurrency 2개 파티션이 2개이면?
     * -> 각 인스턴스에 할당된 파티션 데이터가 수행될테니 concurrency 2개는 의미가 없음
     * -> 파티션 2개인 경우 20초 동안 같은 파티션로부터 온 데이터는 처리 안됨 (스레드당 하나의 파티션을 담당하기 때문)
     * 파티션 3개인 경우
     * -> 파티션 2개 할당 받은 컨슈머에서 하나의 파티션을 처리하고 sleep 빠져도 concurrency 2이기에 다른 파티션 처리
     */
    @KafkaListener(topics = "concurrency-test", groupId = "${spring.kafka.consumer.group-id}", concurrency = "2")
    public void concurrencyTestConsume(ConsumerRecord<String, String> record) {
        log.info("===========concurrency-test=============");
        log.info("Topic: " + record.topic());
        log.info("Partition: " + record.partition());
        log.info("Value: " + record.value());
        log.info("Offset: " + record.offset());
        log.info("===========concurrency-test=============");
        try {
            log.info("===========sleep start=============");
            Thread.sleep(20000);
            log.info("===========sleep end=============");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}