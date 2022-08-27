package com.guo.utils;

import com.alibaba.fastjson.JSONObject;
import com.guo.bean.vo.ResponseResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/
public class WebfluxResponseUtil {
    /**
     * webflux的response返回json对象
     */
    public static Mono<Void> responseWriter(Boolean success, ServerWebExchange exchange, int httpStatus, String msg) {
        if(success){
            return responseWrite(exchange, httpStatus, ResponseResult.success(msg));
        }else {
            return responseWrite(exchange,httpStatus,ResponseResult.failed(String.valueOf(httpStatus),msg));
        }

    }

    public static Mono<Void> responseFailed(ServerWebExchange exchange, String msg,ResponseResult responseResult) {
        return responseWrite(exchange, HttpStatus.INTERNAL_SERVER_ERROR.value(),responseResult);
    }

    public static Mono<Void> responseFailed(ServerWebExchange exchange, int code, int httpStatus, String msg,ResponseResult responseResult) {
        return responseWrite(exchange, httpStatus, responseResult);
    }

    public static Mono<Void> responseFailed(ServerWebExchange exchange, int httpStatus, String msg,ResponseResult responseResult) {
        return responseWrite(exchange, httpStatus, responseResult);
    }

    public static Mono<Void> responseWrite(ServerWebExchange exchange, int httpStatus, ResponseResult result) {
        if (httpStatus == 0) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setAccessControlAllowCredentials(true);
        response.getHeaders().setAccessControlAllowOrigin("*");
        response.setStatusCode(HttpStatus.valueOf(httpStatus));
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(JSONObject.toJSONString(result).getBytes(Charset.defaultCharset()));
        return response.writeWith(Mono.just(buffer)).doOnError((error) -> {
            DataBufferUtils.release(buffer);
        });
    }
}
