package com.guo.bean.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author guokaifeng
 * @createDate: 2022/5/23
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeiXinLoginReturn {
    private String openid;
    private String session_key;
}
