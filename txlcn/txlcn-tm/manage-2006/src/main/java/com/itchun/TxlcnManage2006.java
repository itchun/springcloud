package com.itchun;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableTransactionManagerServer
@EnableEurekaClient
public class TxlcnManage2006 {

	public static void main(String[] args) {
		SpringApplication.run(TxlcnManage2006.class, args);
	}

}
