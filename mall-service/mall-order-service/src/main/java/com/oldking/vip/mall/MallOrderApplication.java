package com.oldking.vip.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@MapperScan(basePackages = {"com.oldking.vip.mall.order.mapper"})
@EnableFeignClients(basePackages = {"com.oldking.vip.mall.goods.feign","com.oldking.vip.mall.cart.feign"})
@SpringBootApplication
public class MallOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallOrderApplication.class,args);
    }
}
