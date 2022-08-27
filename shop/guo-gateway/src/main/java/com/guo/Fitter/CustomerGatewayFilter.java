package com.guo.Fitter;

import com.guo.bean.vo.ResponseResult;
import com.guo.service.RedisService;
import com.guo.utils.RedisUtils;
import com.guo.utils.WebfluxResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.concurrent.TimeUnit;

/**
 * @author guokaifeng
 * @createDate: 2022/5/25
 **/
@Component
public class CustomerGatewayFilter implements GatewayFilter, Ordered {


    @Override
    public int getOrder() {
        return 1;
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("token");
        RedisUtils redisUtils = new RedisUtils(new StringRedisTemplate());
        if(token == null || !redisUtils.hasKey(token)){
            return WebfluxResponseUtil.responseFailed(exchange,"用户没有登录", ResponseResult.failed("-001","用户没有登录"));
        }else {
            redisUtils.expire(token,1800, TimeUnit.SECONDS);
        }
        return chain.filter(exchange);
    }
}
