# README

# 카프카 학습

---

### 명령어 모음

- 주키퍼 실행
    - bin/zookeeper-server-start.sh config/zookeeper.properties
- 카프카 브로커 실행
    - bin/kafka-server-start.sh config/server.properties
- 카프카 토픽 생성
    - bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --topic 토픽명
- 토픽 상세 정보
    - bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic 토픽명 --describe
- 프로듀서 실행
    - bin/kafka-console-producer.sh --bootstrap-server [localhost:9092](http://localhost:9092) --topic 토픽명 --property “parse.key=true” --property “key.separator=:”
- 컨슈머 실행
    - bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic  토픽명 --property print.key=true --property key.separator=" : " --from-beginning


### 카프카 스프링 라이브러리

- Kafka Listener 구현체: KafkaMessageListenerContainer.java
    - message poll: 1244 Line

