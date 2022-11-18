package com.itchun.logger;


import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置全局可以用
 */
//@Configuration
public class FeginLoggerConfig {

//    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
