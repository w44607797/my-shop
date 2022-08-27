package com.guo.Fitter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;

/**
 * @author guokaifeng
 * @createDate: 2022/5/25
 **/

public class CustomerGatewayFilterFactory extends AbstractGatewayFilterFactory {
    @Override
    public GatewayFilter apply(Object config) {
        return new CustomerGatewayFilter();
    }
}
