package com.guo.config;

import com.guo.Fitter.BasicGatewayFilterFactory;
import com.guo.Fitter.CustomerGatewayFilterFactory;
import com.guo.utils.RedisUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author guokaifeng
 * @createDate: 2022/5/25
 **/
@Configuration
public class GatewayConfig {
    @Bean
    public CustomerGatewayFilterFactory myCustomerGatewayFilterFactory() {
        return new CustomerGatewayFilterFactory();
    }
    @Bean
    public BasicGatewayFilterFactory myBasicGatewayFilterFactory() {
        return new BasicGatewayFilterFactory();
    }



}
