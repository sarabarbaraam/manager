package com.sarabarbara.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ManagerApplication {

	static void main(String[] args) {
		SpringApplication.run(ManagerApplication.class, args);
	}

}
