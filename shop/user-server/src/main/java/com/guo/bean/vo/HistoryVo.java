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
public class HistoryVo {
    private String index;
    private String id;
    private String photoUrl;
    private String description;
    private String name;
    private String data;
}
