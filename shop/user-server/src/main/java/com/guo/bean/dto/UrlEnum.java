package com.guo.bean.dto;

/**
 * @author guokaifeng
 * @createDate: 2022/4/25
 **/

public enum UrlEnum {

    USER_HEADSHOT("/headshot");


    private String url;
    UrlEnum(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }
}
