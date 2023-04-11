package com.ailen.exception;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * @author
 */
public class AppAssert {

    public static void isNull(@Nullable Object object, String message) {
        if (object != null) {
            throw new AppException(message);
        }
    }

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new AppException(message);
        }
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new AppException(message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        isTrue(!expression, message);
    }

    public static void notEmpty(String value, String message) {
        isTrue(StrUtil.isNotEmpty(value), message);
    }

    public static void notEmpty(Collection<?> collection, String message) {
        isTrue(CollectionUtil.isNotEmpty(collection), message);
    }

    public static void notEmpty(Map<?, ?> map, String message) {
        isTrue(MapUtil.isNotEmpty(map), message);
    }

    public static void notEmpty(Object[] array, String message) {
        isTrue(ArrayUtil.isNotEmpty(array), message);
    }
}
