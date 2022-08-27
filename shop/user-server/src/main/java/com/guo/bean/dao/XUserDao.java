package com.guo.bean.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author guokaifeng
 * @createDate: 2022/4/14
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class XUserDao implements Serializable {
    private String openId;
    private String session_key;
    private String permission;
    private String type;
}
