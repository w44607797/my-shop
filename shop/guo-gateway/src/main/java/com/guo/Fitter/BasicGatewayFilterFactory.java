package com.guo.Fitter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @author guokaifeng
 * @createDate: 2022/5/26
 **/

public class BasicGatewayFilterFactory extends AbstractGatewayFilterFactory {
    @Override
    public GatewayFilter apply(Object config) {
        return new BasicGatewayFilter();
    }
}
