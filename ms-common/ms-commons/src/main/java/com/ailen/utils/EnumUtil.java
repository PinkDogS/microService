package com.ailen.utils;

import java.lang.reflect.Method;

/**
 * 枚举工具类
 *
 * @author anshare
 * @version 1.0
 *
 **/
public class EnumUtil {

    public static boolean contains(Class<?> clazz, Object value) throws Exception {
        boolean include = false;
        if (clazz.isEnum()) {
            Object[] enumConstants = clazz.getEnumConstants();
            Method getCode = clazz.getMethod("getCode");
            for (Object enumConstant : enumConstants) {
                if (getCode.invoke(enumConstant).equals(value)) {
                    include = true;
                    break;
                }
            }
        }
        return include;
    }

}
