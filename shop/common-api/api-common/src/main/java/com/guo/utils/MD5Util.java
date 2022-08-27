package com.guo.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @author guokaifeng
 * @createDate: 2022/4/4
 **/

public class MD5Util {
    private static final Digester digester = new Digester(DigestAlgorithm.MD5);

    public static String getMD5(String message){
        return digester.digestHex(message);
    }
}
