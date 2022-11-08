package com.itchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SeataATTM2007 {

	public static void main(String[] args) {
		SpringApplication.run(SeataATTM2007.class, args);
	}

}
