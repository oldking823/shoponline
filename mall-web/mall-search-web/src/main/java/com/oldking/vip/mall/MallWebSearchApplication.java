package com.oldking.vip.mall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: http://localhost:8085/web/search
 */
@EnableFeignClients(basePackages = "com.oldking.vip.mall.search.feign")
@SpringBootApplication
public class MallWebSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallWebSearchApplication.class,args);
    }
}