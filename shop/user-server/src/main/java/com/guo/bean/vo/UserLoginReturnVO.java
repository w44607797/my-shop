package com.guo.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaifeng
 * @createDate: 2022/4/14
 **/


/**
 * 用户登录调用微信云api实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginReturnVO {
    private String openid;
    private String session_key;
    private String unionid;
    private String errcode;
    private String errmsg;

}
