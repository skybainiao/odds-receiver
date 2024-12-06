package com.example.odds_receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication

public class OddsReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(OddsReceiverApplication.class, args);
	}

}
