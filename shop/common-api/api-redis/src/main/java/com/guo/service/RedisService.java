package com.guo.service;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/

public interface RedisService {
    void storeCode(String phone,String code);
    String getCode(String phone);
    void setToken(String phone,String token);
    String getUserPhoneByToken(String token);
    String getUserPermissionByToken(String token);
}
