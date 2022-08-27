package com.guo.controller;

import com.guo.bean.vo.ResponseResult;
import com.guo.bean.vo.UserLoginVO;
import com.guo.exception.TotalException;
import com.guo.service.UserService;
import com.guo.service.WeiXinService;
import com.guo.utils.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @author guokaifeng
 * @createDate: 2022/5/23
 **/
@RequestMapping("/api/user/weixin")
@RestController
@CrossOrigin
public class WeiXinUserController {

    @Autowired
    WeiXinService weiXinService;

    @Autowired
    UserService userService;

    @PostMapping("/passport/login")
    public ResponseResult<String> userLogin(String code) throws TotalException, IOException {
        ResponseResult<String> responseResult = new ResponseResult<>();
        String openid = weiXinService.WeiXinUserLogin(code);
        if(openid==null){
            throw new TotalException(StateEnum.WEIUSER_ERROR_WRONGCODE.getCode(),StateEnum.WEIUSER_ERROR_WRONGCODE.getMessage());
        }
        String s = userService.getJWTByPhone(openid);
        responseResult.setData(s);
        return responseResult;
    }



}
