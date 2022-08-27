package com.guo.exception;

import com.guo.utils.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/
@ControllerAdvice
@Slf4j
public class DatabaseExceptionHandle {

    @ResponseBody
    @ExceptionHandler(SQLException.class)
    public void throwSqlException(TotalException e) throws TotalException {
        throw new TotalException(StateEnum.SERVICE_DATABASE.getCode(),"请联系管理员处理",e.getMessage());

    }

}
