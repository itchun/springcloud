package com.itchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClient2000 {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClient2000.class, args);
	}

}
