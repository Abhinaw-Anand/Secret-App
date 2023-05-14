package com.example.SpringInitial.Service;

import com.example.SpringInitial.Model.PasswordModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class KafkaConsumerService
{
    SavePasswordService savePasswordService;
    ObjectMapper objectMapper;

    @KafkaListener(topics = "PasswordTopic",groupId = "PasswordSavingService")
    public void PasswordTopicconsumer(String message) throws Exception
    {
        log.info("Received kafka message is for topic PasswordTopic is {} ",message);
        PasswordModel passwordModel=objectMapper.readValue(message,PasswordModel.class);
        savePasswordService.savePasswordToMongoDB(passwordModel);

    }

}
