package com.guo.service;

import com.guo.bean.dao.UserInfoDao;
import com.guo.bean.vo.UserInfoVo;

public interface UserInfoService {
     UserInfoDao getUserInfo(String phone);
     void upDateUserImg(UserInfoDao userInfoDao);
     String getUrl(String phone);
     void upDateUser(UserInfoVo userInfoVo);
}
