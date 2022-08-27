package com.guo.service.impl;

import com.alibaba.fastjson.JSON;
import com.guo.bean.vo.WeiXinLoginReturn;
import com.guo.service.WeiXinService;
import com.guo.utils.DozerUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author guokaifeng
 * @createDate: 2022/5/23
 **/
@Service
public class WeiXinServiceImpl implements WeiXinService {

    @Autowired
    Mapper dozerBeanMapper;

    @Override
    public String WeiXinUserLogin(String code) throws IOException {
        Document document = Jsoup.connect("https://api.weixin.qq.com/sns/jscode2session?appid=wxb784319471ec6cec&secret=9ce06eda4cc99398773cf6413b801e59&js_code=" + code + "&grant_type=authorization_code").get();
        String text = document.body().text();
        Object parse = JSON.parse(text);
        WeiXinLoginReturn weiXinLoginReturn = new WeiXinLoginReturn();
        dozerBeanMapper.map(parse,weiXinLoginReturn);
        String openid = weiXinLoginReturn.getOpenid();
        return openid;
    }
}
