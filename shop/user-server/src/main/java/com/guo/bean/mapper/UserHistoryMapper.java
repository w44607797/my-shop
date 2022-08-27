package com.guo.bean.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guo.bean.dao.UserCollectDao;
import com.guo.bean.dao.UserHistoryDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author guokaifeng
 * @createDate: 2022/5/23
 **/
@Mapper
@Repository
public interface UserHistoryMapper extends BaseMapper<UserHistoryDao> {
    List<UserHistoryDao> selectall(String user_id);

}
