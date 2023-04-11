package com.ailen.utils;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 树配置
 *
 * @author jimin
 * @since 2023-02-08 13:09:32
 */
@Data
@Accessors(chain = true)
public class TreeConfig {
    private String idKey = "id";
 
    private String parentIdKey = "parentId";
 
    private String childrenKey = "children";
}
