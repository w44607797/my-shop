package com.guo.bean.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.bean.dao.UserCollectDao;
import com.guo.bean.dao.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/
@Mapper
@Repository
public interface UserCollectMapper extends BaseMapper<UserCollectDao> {
    List<UserCollectDao> selectall(String user_id);
}
