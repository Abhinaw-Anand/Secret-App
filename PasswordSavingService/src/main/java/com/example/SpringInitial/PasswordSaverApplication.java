package com.example.SpringInitial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class PasswordSaverApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasswordSaverApplication.class, args);
	}

}
