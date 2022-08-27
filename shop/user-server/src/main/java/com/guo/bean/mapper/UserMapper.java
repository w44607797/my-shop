package com.guo.bean.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guo.bean.dao.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author guokaifeng
 * @createDate: 2022/4/14
 **/

@Mapper
@Repository
public interface UserMapper extends BaseMapper<UserDao> {
    int checkIsRegister(String phone);
}
