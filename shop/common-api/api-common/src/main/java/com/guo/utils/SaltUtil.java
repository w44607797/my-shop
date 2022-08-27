package com.guo.utils;

import java.util.Random;

/**
 * @author guokaifeng
 * @createDate: 2022/4/5
 **/

public class SaltUtil {
    public static String getSalt(int n){
        char[] chars="bakfbaowbfjkabofbaCCUVUIVIWDKV%$&^%*&*%$1131415".toCharArray(); //转换为字符数组
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<n;i++){
            char ch=chars[new Random().nextInt(chars.length)];
            stringBuilder.append(ch);
        }
        return stringBuilder.toString();
    }
}
