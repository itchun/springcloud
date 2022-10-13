package com.itchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //开启注册中心
public class EurekaService1001 {

	public static void main(String[] args) {
		SpringApplication.run(EurekaService1001.class, args);
	}

}
