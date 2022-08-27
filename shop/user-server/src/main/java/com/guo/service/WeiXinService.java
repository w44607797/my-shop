package com.guo.service;

import java.io.IOException;

/**
 * @author guokaifeng
 * @createDate: 2022/5/23
 **/

public interface WeiXinService {
    String WeiXinUserLogin(String code) throws IOException;

}
