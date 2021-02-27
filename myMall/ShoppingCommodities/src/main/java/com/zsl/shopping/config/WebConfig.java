package com.zsl.shopping.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created with IntelliJ IDEA.
 * User: 15625
 * Date: 2021/2/14
 * Time: 12:13
 * Description: No Description
 */
@Configuration
public class WebConfig  extends WebMvcConfigurationSupport {
    /**
     * 资源映射配置
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**").addResourceLocations("file:D:/picture/");
    }
}
