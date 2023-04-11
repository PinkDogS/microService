package com.ailen.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志对象
 * cloud api 用到的接口传输对象
 */
@Data
@Accessors(chain = true)
public class SysLogDto implements Serializable {
    
    private Long id;
    
    /**内容*/
    private String logContent;

    /**日志类型(1:登录日志;2:操作日志;3:定时任务)  */
    private Integer logType;

    /**操作类型(1:添加;2:修改;3:删除;) */
    private Integer operateType;
    
    /**操作人用户账户*/
    private Long userid;
    
    /**操作人用户名*/
    private String loginid;
    
    /**操作人用户真实姓名*/
    private String realname;
    
    private String ip;
    
    /**请求方法 */
    private String method;
    
    /**请求路径*/
    private String requestUrl;

    /**请求参数 */
    private String requestParam;

    /**请求类型*/
    private String requestType;
    
    /**耗时 ms*/
    private Long costTime;
    
    /** 日志日期 */
    private String logDate;
    
    /** 创建人 */
    private String createBy;
    
    /** 创建时间 */
    private Date createTime;

}
