package com.spring_training.config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringTrainingConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTrainingConfigServerApplication.class, args);
	}

}
