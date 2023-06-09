package com.example.SpringInitial.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaProducerService
{

    private KafkaTemplate<String, String> kafkaTemplate;
    public void sendMessage(String message)
    {
        log.info("message sent in kafka topic  PasswordTopic is: {}", message);
        kafkaTemplate.send("PasswordTopic",message);
    }

}
