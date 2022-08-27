package com.guo;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author guokaifeng
 * @createDate: 2022/5/1
 **/

@SpringBootApplication
@EnableDiscoveryClient
public class CloudGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(CloudGatewayApplication.class, args);
    }
    @Bean
    public Mapper dozerMapper(){
        return new DozerBeanMapper();
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
