package com.example.SpringInitial.Configs;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class PasswordSavingServiceConfiguration
{
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

}
