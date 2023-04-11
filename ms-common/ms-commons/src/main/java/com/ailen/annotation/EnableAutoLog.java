package com.ailen.annotation;

import com.anshare.base.common.constant.CommonConstant;

import java.lang.annotation.*;

/**
 * 系统日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableAutoLog {

	/**
	 * 日志内容
	 */
	String value() default "";

	/**
	 * 日志类型
	 * 1:登录日志 2:操作日志 3:定时任务(保留);
	 */
	int logType() default CommonConstant.LOG_TYPE_OPERATE;
	
	/**
	 * 操作日志类型
	 * 1查询，2添加，3修改，4删除
	 */
	int operateType() default 0;

}
