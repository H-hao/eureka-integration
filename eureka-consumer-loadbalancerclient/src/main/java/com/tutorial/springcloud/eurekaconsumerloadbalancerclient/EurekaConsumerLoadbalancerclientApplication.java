package com.tutorial.springcloud.eurekaconsumerloadbalancerclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EurekaConsumerLoadbalancerclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaConsumerLoadbalancerclientApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
