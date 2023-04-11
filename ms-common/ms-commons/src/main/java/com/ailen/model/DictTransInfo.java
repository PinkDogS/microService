package com.ailen.model;

import com.anshare.base.common.enums.TransPolicyEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典转换信息
 *
 * @author jimin
 * @since 2023-02-17 15:49:11
 */
@Data
@Accessors(chain = true)
public class DictTransInfo {
    TransPolicyEnum policy;
    
    /**
     * 字典定义和字典翻译值映射
     */
    Map<String, Map<Object, String>> dictCodeAndDataMap = new HashMap<>();
    
    /**
     * 字典定义和字典翻译值对象映射
     */
    Map<String, Map<Object, Object>> dictCodeAndDataObjMap = new HashMap<>();
    
}
