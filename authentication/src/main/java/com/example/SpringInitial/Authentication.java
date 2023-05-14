package com.example.SpringInitial;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.TimeZone;


@SpringBootApplication
@EnableCaching
@EnableFeignClients
public class Authentication
{
	public static void main(String[] args) {


		SpringApplication.run(Authentication.class, args);
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));


	}

}
