package com.kibong.shoppingwiki.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String send(String topic, String contentsSubject) {

        kafkaTemplate.send(topic, contentsSubject);
        log.info("kafka send topic={} contentsSubject={}", topic, contentsSubject);

        return contentsSubject;
    }
}
