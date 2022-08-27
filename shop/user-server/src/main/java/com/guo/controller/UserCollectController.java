package com.guo.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guo.bean.dao.UserCollectDao;
import com.guo.bean.dao.UserDao;
import com.guo.bean.mapper.UserMapper;
import com.guo.bean.vo.CollectVo;
import com.guo.bean.vo.ResponseResult;
import com.guo.config.RabbitMQConfig;
import com.guo.exception.TotalException;
import com.guo.service.RedisService;
import com.guo.service.UserService;
import com.guo.service.impl.UserServiceImpl;
import com.guo.utils.DozerUtils;
import com.guo.utils.RedisUtils;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/
@RestController
@RequestMapping("/api/user/collect")
@CrossOrigin
@Slf4j
public class UserCollectController {

    public static final String QUEUE_NAME="collect";

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

    /**
     * 添加入收藏
     */

    @PostMapping("/add")
    public ResponseResult addToCollection(HttpServletRequest request, String id, String index) throws TotalException {
        String token = request.getHeader("token");
        String phone = redisService.getUserPhoneByToken(token);
        userService.putUserCollect(phone, id, index);
        String key = phone+":collect";
        if(redisUtils.hasKey(key)){
           redisUtils.hPut(key,index,"1");
        }else {
            redisUtils.hIncrBy(key,index,1);
        }
        try {
//            restTemplate.getForObject("http://monitor-server/api/monitor/addcollect/" + phone + "/" + index, ResponseResult.class);
            sendMessageToMonitor(phone+","+index);
        }catch (Exception e){
            e.printStackTrace();
            log.error("添加收藏权重失败");
        }

        return ResponseResult.success();
    }

    /**
     * 获取用户收藏列表
     *
     * @param request
     */
    @GetMapping("/get/{begin}/{num}")
    public ResponseResult getUserCollection(HttpServletRequest request, @PathVariable("begin") String begin,
                                            @PathVariable("num") String num) {
        String token = request.getHeader("token");
        String phone = redisService.getUserPhoneByToken(token);
//        String phone = "17759048528";
        List<UserCollectDao> collects = userService.getUserCollect(phone, begin, num);
        ArrayList<CollectVo> arrayList = new ArrayList<>(collects.size());
        int temp = 0;
        for (UserCollectDao collect : collects) {
            String oid = collect.getOid();
            String myIndex = collect.getMyIndex();
            ResponseResult responseResult = restTemplate.getForObject("http://es-server/api/search/get/" + oid+"/"+myIndex, ResponseResult.class);
            Object parse = JSON.parse(JSON.toJSONString(responseResult.getData()));
            CollectVo collectVo = new CollectVo();
            dozerBeanMapper.map(parse,collectVo);
            collectVo.setIndex(collect.getMyIndex());
            arrayList.add(collectVo);
        }
        return ResponseResult.success(arrayList.toArray());
    }

    @GetMapping("/getIndex/{phone}")
    public ResponseResult getUserCollectIndexNum(@PathVariable String phone){
        List<UserCollectDao> collect = userService.getUserCollect(phone);

        return null;
    }

    @DeleteMapping("/delete/{id}/{index}")
    public ResponseResult deleteCollect(@PathVariable String id, @PathVariable String index,HttpServletRequest request){
        String token = request.getHeader("token");
        String phone = redisService.getUserPhoneByToken(token);
        userService.deleteCollect(phone,id);
        redisUtils.hIncrBy(phone+":collect",index,-1);
        return ResponseResult.success();
    }

    public void sendMessageToMonitor(String message) throws IOException, TimeoutException {
        Channel channel = RabbitMQConfig.getChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());

    }

}
