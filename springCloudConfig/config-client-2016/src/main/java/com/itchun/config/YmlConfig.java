package com.itchun.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "itchun")
@Component
@Getter
@Setter
public class YmlConfig {

    private String name;

    private String age;

    private String sex;

    private String see;

    private String book;

    private String app;

    private String list;

    private String map;

    private String abb;

    private String adf;

    private String dss;

    private String d345;

    private String qwe;

    private String sdf;

    private String drt;

    private String hyy;

    private String bool;

    private String stri;

    private String poy;

    private String ppppp;
}
