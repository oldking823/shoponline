package com.oldking.vip.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.oldking.vip.mall.user.mapper"})
public class MallUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallUserApplication.class,args);
    }
}
