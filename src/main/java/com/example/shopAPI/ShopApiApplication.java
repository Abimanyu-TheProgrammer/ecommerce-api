package com.example.shopAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class ShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApiApplication.class, args);
	}

}
