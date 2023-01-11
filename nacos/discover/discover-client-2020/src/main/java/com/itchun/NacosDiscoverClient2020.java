package com.itchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class NacosDiscoverClient2020 {

	public static void main(String[] args) {
		SpringApplication.run(NacosDiscoverClient2020.class, args);
	}

}
