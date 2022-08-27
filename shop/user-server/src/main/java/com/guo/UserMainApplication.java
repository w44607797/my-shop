package com.guo;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.guo.bean.mapper")
public class UserMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserMainApplication.class,args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Mapper dozerMapper(){
        return new DozerBeanMapper();
    }
}
