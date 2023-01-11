package com.itchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosDiscoverClient2022 {

	public static void main(String[] args) {
		SpringApplication.run(NacosDiscoverClient2022.class, args);
	}

}
