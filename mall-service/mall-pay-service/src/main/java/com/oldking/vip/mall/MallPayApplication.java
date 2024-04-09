package com.oldking.vip.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = {"com.oldking.vip.mall.pay.mapper"})
@SpringBootApplication
public class MallPayApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallPayApplication.class,args);
    }
}
