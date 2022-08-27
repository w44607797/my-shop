package com.guo.bean.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guo.bean.dao.UserDao;
import com.guo.bean.dao.UserInfoDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfoDao> {

}
