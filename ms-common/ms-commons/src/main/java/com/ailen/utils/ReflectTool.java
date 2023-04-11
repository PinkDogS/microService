package com.ailen.utils;

import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 反射工具类
 *
 * @author anshare
 * @date
 */
public class ReflectTool {

    /**
     * 获得某个对象的字段和字段值映射
     *
     * @param obj
     * @param beanClass
     * @return
     * @author anshare
     * @date
     */
    public static Map<String, Object> mapFieldAndValue(Object obj, Class<?> beanClass) {
        Map<String, Object> map = new HashMap<>(0);
        Field[] fieldList = ReflectUtil.getFields(beanClass);
        for (Field field : fieldList) {
            Object value = ReflectUtil.getFieldValue(obj, field);
            map.put(field.getName(), value);
        }
        return map;
    }
}
