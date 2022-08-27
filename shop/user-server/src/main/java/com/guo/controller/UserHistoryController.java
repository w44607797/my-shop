package com.guo.controller;

import com.alibaba.fastjson.JSON;
import com.guo.bean.dao.UserCollectDao;
import com.guo.bean.dao.UserHistoryDao;
import com.guo.bean.vo.CollectVo;
import com.guo.bean.vo.HistoryVo;
import com.guo.bean.vo.ResponseResult;
import com.guo.exception.TotalException;
import com.guo.service.RedisService;
import com.guo.service.UserService;
import com.guo.utils.RedisUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author guokaifeng
 * @createDate: 2022/5/9
 **/

@RestController
@RequestMapping("/api/user/history")
@CrossOrigin
public class UserHistoryController {

    @Autowired
    UserService userService;

    @Resource
    RedisService redisService;

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    Mapper dozerBeanMapper;

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping("/get/{begin}/{num}")
    public ResponseResult getUserHistoryList(@PathVariable("begin")String begin,
                                             @PathVariable("num")String num, HttpServletRequest request){

        String token = request.getHeader("token");
        String phone = redisService.getUserPhoneByToken(token);
        List<UserHistoryDao> collects = userService.getUserHistory(phone, begin, num);
        ArrayList<HistoryVo> arrayList = new ArrayList<>(collects.size());
        int temp = 0;
        for (UserHistoryDao collect : collects) {
            String oid = collect.getOid();
            String myIndex = collect.getMyIndex();
            ResponseResult responseResult = restTemplate.getForObject("http://es-server/api/search/get/" + oid+"/"+myIndex, ResponseResult.class);
            Object parse = JSON.parse(JSON.toJSONString(responseResult.getData()));
            HistoryVo historyVo = new HistoryVo();
            dozerBeanMapper.map(parse,historyVo);
            historyVo.setIndex(collect.getMyIndex());
            arrayList.add(historyVo);
        }
        return ResponseResult.success(arrayList.toArray());

    }

    @PostMapping("/add")
    public ResponseResult addToHistory(HttpServletRequest request, String id, String index) throws TotalException {
        String token = request.getHeader("token");
        String phone = redisService.getUserPhoneByToken(token);

        userService.putUserHistory(phone, id, index);
        return ResponseResult.success();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseResult deleteHistory(@PathVariable String id,HttpServletRequest request){
        String token = request.getHeader("token");
        String phone = redisService.getUserPhoneByToken(token);
        userService.deleteHistory(phone,id);
        return ResponseResult.success();
    }



}
