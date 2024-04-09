package com.oldking.vip.mall.seata;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SeataDataSourceProxy {
    /**
     * 获取数据源对象 DruidDataSource
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    /**
     * 基于DruidDataSource闯将代理数据源DataSourceProxy
     */
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }

    /**
     * 如果是多数据源需要切换
     */

    /**
     * 替换MyBatis的数据源（DataSourceProxy）
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public MybatisSqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSourceProxy){
//        使用MybatisSqlSessionFactoryBean 代替了 SqlSessionFactoryBean，否则MyBatisPlus不会生效
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSourceProxy);
        return mybatisSqlSessionFactoryBean;
    }

}
