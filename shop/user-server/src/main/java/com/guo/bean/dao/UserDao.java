package com.guo.bean.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author guokaifeng
 * @createDate: 2022/4/4
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("app_user")
public class UserDao implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    private String phone;
    private String password;
    private String headshot;
    private String salt;
    private String permission;
    private String name;
}
