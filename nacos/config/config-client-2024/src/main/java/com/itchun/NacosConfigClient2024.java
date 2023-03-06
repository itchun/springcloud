package com.itchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NacosConfigClient2024 {

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigClient2024.class, args);
    }

}
