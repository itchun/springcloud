package com.itchun.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@ConfigurationProperties(value = "itchun")
@Component
@Getter
@Setter
public class YmlConfig {

    private String data;

    private String name;

    private Map<String, String> param;
}
