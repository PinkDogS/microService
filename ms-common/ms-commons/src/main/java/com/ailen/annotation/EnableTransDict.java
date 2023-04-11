package com.ailen.annotation;

import com.anshare.base.common.enums.ReturnTypeEnum;
import com.anshare.base.common.enums.TransPolicyEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字典自动转换注解
 *
 * @author jimin
 * @since 2023-02-17 14:03:42
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableTransDict {
    /**
     * 转换策略
     */
    TransPolicyEnum policy() default TransPolicyEnum.SYS_DICT;
    
    
    /**
     * 字典代码
     * 当转换策略为系统字典时有效
     */
    String dictCode() default "";
    
    /**
     * 转换返回类型，默认文本
     * 适用策略：TransPolicyEnum.SYS_DICT
     */
    ReturnTypeEnum resType() default ReturnTypeEnum.TEXT;
    
    /**
     * 转换后字段名称
     * 如果指定，直接将转换结果放置到该字段
     * @author jimin
     */
    String fieldName() default "";

    /**
     * 转换后新字段后缀
     * field字段不为空时有效
     * field为空时，如果 postfix 为空，根据返回类型，自动采用 文本类: _TEXT 对象类: _OBJECT
     *
     * @author jimin
     */
    String postfix() default "";
    
}
