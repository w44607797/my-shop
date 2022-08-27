package com.guo.service.impl;

import com.guo.service.RedisService;
import com.guo.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    RedisUtils redisUtils;


    /**
     * 存储对应手机号的验证码
     * @param phone
     * @param code
     */

    @Override
    public void storeCode(String phone,String code) {
        redisTemplate.opsForValue().set(phone+":code",code,1800, TimeUnit.SECONDS);
    }

    /**
     * 获取对应手机号的验证码
     * @param phone
     * @return
     */
    @Override
    public String getCode(String phone) {
        return redisTemplate.opsForValue().get(phone+":code");
    }


    /**
     * 设置token对应的参数
     * filed1:filed2
     * phone:permission
     * @param phone
     * @param token
     */

    @Override
    public void setToken(String phone,String token) {
        redisUtils.hPut(token,"phone",phone);
        redisUtils.expire(token,1800,TimeUnit.SECONDS);
    }

    @Override
    public String getUserPhoneByToken(String token) {
        return (String) redisUtils.hGet(token,"phone");
    }

    @Override
    public String getUserPermissionByToken(String token) {
        return (String) redisUtils.hGet(token,"permission");
    }

}
