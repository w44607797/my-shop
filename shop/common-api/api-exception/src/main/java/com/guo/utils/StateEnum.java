package com.guo.utils;

import lombok.AllArgsConstructor;

/**
 * @author guokaifeng
 * @createDate: 2022/4/14
 **/

@AllArgsConstructor
public enum StateEnum {

    USER_ERROR_NOREGISTER("001","用户未注册"),
    USER_ERROR_HASBEENREGISTER("002","该手机号已经注册过了"),
    USER_ERROR_WRONGPASSWORD("003","用户密码错误"),
    USER_ERROR_WRONGCODE("004","登录验证码出错"),
    USER_ERROR_FAILEDTOBINGDINGJWT("005","用户绑定JWT失败"),
    USER_ERROR_ERRORFORMAT("006","数据校验出错"),
    USER_ERROR_NOLOGIN("007","用户还没有登录"),
    USER_ERROR_WRONGJWT("008","用户身份过期"),
    USER_ERROR_HEADSHOTISNULL("009","用户上传的头像为空"),
    USER_ERROR_WRONGPHONEORPASSWORD("010","用户账号或密码出错"),
    WEIUSER_ERROR_WRONGCODE("011","微信用户错误的code码"),
    USER_ERROR_INFO("012","用户没有更新资料"),
    JSOUP_SCANBARCODE_NORESPONSE("900","获取药品条形码信息失败"),

    FILE_FAILED("901","获取图片失败"),
    SERVICE_ERROR_FAILEDTOGETCODE("300","返回验证码失败"),
    ELASTICSEARCH_NOFOUND("301","没有找到对应属性的信息"),
    DATABASE_ERROR_MESSAGE("500","服务端出错,请联系管理员"),
    DATABASE_ERROR_FAILEDTOGETUSER("501","数据库获取用户信息出错"),
    SERVICE_DATABASE("900","操作数据库出错"),
    SERVICE_GETRECOMMEND("901","请求数量过多");

    private String code;
    private String message;

    public String getCode(){
        return this.code;
    }
    public String getMessage(){
        return this.message;
    }

}
