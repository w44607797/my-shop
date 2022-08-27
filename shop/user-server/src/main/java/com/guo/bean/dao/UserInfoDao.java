package com.guo.bean.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfoDao implements Serializable {
    @TableId
    private String phone;
    private String name;
    private String age;
    @TableField("img_url")
    private String imgUrl;
    private String gender;
    private String birthday;
    private String email;
}
