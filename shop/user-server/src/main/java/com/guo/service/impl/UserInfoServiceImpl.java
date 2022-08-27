package com.guo.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.guo.bean.dao.UserDao;
import com.guo.bean.dao.UserInfoDao;
import com.guo.bean.mapper.UserInfoMapper;
import com.guo.bean.mapper.UserMapper;
import com.guo.bean.vo.UserInfoVo;
import com.guo.service.UserInfoService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {


    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    Mapper dozerMapper;

    @Override
    public UserInfoDao getUserInfo(String phone) {
        UserInfoDao userInfoDao = userInfoMapper.selectById(phone);
        return userInfoDao;
    }

    @Override
    public void upDateUserImg(UserInfoDao userInfoDao) {
        UpdateWrapper<UserInfoDao> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("phone",userInfoDao.getPhone());
        userInfoMapper.update(userInfoDao,updateWrapper);
    }

    @Override
    public String getUrl(String phone) {
        UserInfoDao userInfoDao = userInfoMapper.selectById(phone);
        String imgUrl = userInfoDao.getImgUrl();
        return imgUrl;
    }

    @Override
    public void upDateUser(UserInfoVo userInfoVo) {
        UserInfoDao userInfoDao = dozerMapper.map(userInfoVo, UserInfoDao.class);
        if(userInfoMapper.selectById(userInfoVo.getPhone())!=null){
            userInfoMapper.insert(userInfoDao);
        }else {
            UpdateWrapper<UserInfoDao> updateWrapper = new UpdateWrapper();
            updateWrapper.eq("phone",userInfoVo.getPhone());
            userInfoMapper.update(userInfoDao,updateWrapper);
        }
    }
}
