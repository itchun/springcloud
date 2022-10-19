package com.itchun;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDistributedTransaction
@EnableEurekaClient
@SpringBootApplication
public class TxlcnClient2004 {

	public static void main(String[] args) {
		SpringApplication.run(TxlcnClient2004.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate rest() {
		return new RestTemplate();
	}
}
