package com.zsl.commodityprovider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan(basePackages = "com.zsl.commodityprovider.persistence")
public class CommodityProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommodityProviderApplication.class, args);
    }

}
