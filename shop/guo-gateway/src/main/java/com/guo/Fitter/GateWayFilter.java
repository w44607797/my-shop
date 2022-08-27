package com.guo.Fitter;

import com.guo.bean.vo.ResponseResult;
import com.guo.service.RedisService;
import com.guo.utils.RedisUtils;
import com.guo.utils.WebfluxResponseUtil;
import com.guo.utils.XssCleanRuleUtils;
import io.netty.buffer.ByteBufAllocator;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author guokaifeng
 * @createDate: 2022/5/2
 **/

@Component
@Slf4j
public class  GateWayFilter implements GlobalFilter, Ordered {

    private String[] sqlinjectionHttpUrls = new String[0];


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String contentType = serverHttpRequest.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);

        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        String method = request.getMethodValue();
        if ((HttpMethod.POST.name().equals(method) || HttpMethod.PUT.name().equals(method))) {
            String rawQuery = uri.getRawQuery();
//            if (StringUtils.isBlank(rawQuery)){
//                return chain.filter(exchange);
//            }
            try {
                rawQuery = XssCleanRuleUtils.xssClean(rawQuery);
            }catch (Exception e){
                log.error("rawquery清理出错");
            }
            URI newUri = UriComponentsBuilder.fromUri(uri)
                    .replaceQuery(rawQuery)
                    .build(true)
                    .toUri();

//            byte[] oldBytes = new byte[dataBuffer.readableByteCount()];
//            dataBuffer.read(oldBytes);
//            String bodyString = new String(oldBytes, StandardCharsets.UTF_8);
//            // 由于修改了传递参数，需要重新设置CONTENT_LENGTH，长度是字节长度，不是字符串长度
//            int length = newBytes.length;
//            headers.remove(HttpHeaders.CONTENT_LENGTH);
//            headers.setContentLength(length);
//            headers.set(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf8");

        }
//        exchange.getRequest().getBody().g
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }
    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

}

