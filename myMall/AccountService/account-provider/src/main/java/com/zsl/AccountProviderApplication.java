package com.zsl;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.zsl.persistence")
@ComponentScan(basePackages = {"com.zsl.persistence", "com.zsl.service"})
@EnableDubbo
public class AccountProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountProviderApplication.class, args);
    }

}
