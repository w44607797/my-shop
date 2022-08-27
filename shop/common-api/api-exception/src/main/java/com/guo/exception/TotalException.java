package com.guo.exception;

import com.guo.utils.StateEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * @author guokaifeng
 * @createDate: 2022/4/7
 **/

@Slf4j
public class TotalException extends Exception{


    private String code;

    public TotalException(String code, String message, String logMess){
        this(message);
        this.code = code;
        log.error(logMess);
    }
    public TotalException(String code, String message){
        this(message);
        this.code = code;
    }
    public TotalException(StateEnum stateEnum){
        this(stateEnum.getMessage());
        this.code = stateEnum.getCode();
    }

    public TotalException(){}
    public TotalException(String message){super(message);}
//    public void setCode(String code){
//        this.code = code;
//    }
    public String getCode(){
        return this.code;
    }
}
