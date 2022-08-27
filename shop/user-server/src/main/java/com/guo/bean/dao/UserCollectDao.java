package com.guo.bean.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_interest")
public class UserCollectDao implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableField("user_id")
    private String userId;
    @TableField("oid")
    private String oid;
    @TableField("my_index")
    private String myIndex;
    @TableId(type= IdType.AUTO)
    private String totalid;

    public UserCollectDao(String userId,String oid,String myIndex){
        this.userId = userId;
        this.myIndex = myIndex;
        this.oid = oid;
    }

}
