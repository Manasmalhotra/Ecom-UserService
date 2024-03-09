package com.example.userservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerConfig {


    private KafkaTemplate<String, String> kafkaTemplate;
    public KafkaProducerConfig(KafkaTemplate<String,String>kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }
    public void sendMessage(String topic,String msg) {
        kafkaTemplate.send(topic, msg);
    }
}
