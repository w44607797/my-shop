package com.guo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

/**
 * @author guokaifeng
 * @createDate: 2022/4/4
 **/

@Component
public class JWTUtil {

    public final static SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String getJWT(String phone){
        return Jwts.builder().setSubject(phone).signWith(key).compact();
    }

}
