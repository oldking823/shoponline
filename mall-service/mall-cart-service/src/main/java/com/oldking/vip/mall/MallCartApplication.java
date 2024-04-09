package com.oldking.vip.mall;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;


@MapperScan(basePackages = {"com.oldking.vip.mall.cart.mapper"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = {"com.oldking.vip.mall.goods.feign"})
public class MallCartApplication {
    public static void main(String[] args) {
        SpringApplication.run(MallCartApplication.class,args);
    }
}
