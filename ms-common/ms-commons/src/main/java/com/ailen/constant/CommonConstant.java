package com.ailen.constant;

/**
 *
 * @author jimin
 */
public interface CommonConstant {
    /**
     * 系统日志类型： 登录/登出
     */
    int LOG_TYPE_LOGON = 1;
    
    /**
     * 系统日志类型： 操作
     */
    int LOG_TYPE_OPERATE = 2;
    
    /**
     * 操作类型：查询
     */
    int OP_TYPE_QRY = 1;
    
    /**
     * 操作类型：添加
     */
    int OP_TYPE_ADD = 2;
    
    /**
     * 操作类型：修改
     */
    int OP_TYPE_MOD = 3;
    
    /**
     * 操作类型：删除
     */
    int OP_TYPE_DEL = 4;
    
    /**
     * 操作类型：导入
     */
    int OP_TYPE_IMPORT = 5;
    
    /**
     * 操作类型：导出
     */
    int OP_TYPE_EXPORT = 6;
    /**
     * 操作类型：其他
     */
    int OP_TYPE_OTHER = 9;
    
    /**
     * 逻辑删除
     * @deprecated 逻辑删除字段使用时间戳，不再固定为1
     */
    int LOGICAL_DELETED = 1;
    
    /**
     * 逻辑存在，与逻辑删除相对应，
     * 要求表字段不为空，默认为0
     *
     */
    int LOGICAL_EXISTS = 0;
    
    /**
     * 平台
     */
    int BELONG_TO_SYSTEM = 0;
    
    /**
     * 应用
     */
    int BELONG_TO_MODULE = 1;
}
