package com.itchun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class OpenFeign2010 {

    public static void main(String[] args) {
//        SpringApplication springApplication = new SpringApplication(OpenFeign2010.class);
//        springApplication.setAllowBeanDefinitionOverriding(true);
//        springApplication.run(args);
        SpringApplication.run(OpenFeign2010.class, args);
    }

}
