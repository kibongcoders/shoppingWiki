package com.kibong.shoppingwiki.kafka;

import com.kibong.shoppingwiki.kafka.dto.ContentsLogMsgDto;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaTemplate<String, JSONObject> kafkaJsonTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaTemplate<String, JSONObject> kafkaJsonTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaJsonTemplate = kafkaJsonTemplate;
    }

    public String send(String topic, String contentsSubject) {

        kafkaTemplate.send(topic, contentsSubject);
        log.info("kafka send topic={} contentsSubject={}", topic, contentsSubject);

        return contentsSubject;
    }

    public JSONObject jsonSend(String topic, JSONObject jsonObject){

        log.info("jsonObject={}", jsonObject);

        kafkaJsonTemplate.send(topic, jsonObject);

        return jsonObject;
    }
}
