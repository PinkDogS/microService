package com.ailen.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author anshare
 * @version 1.0
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Right implements Serializable {

    private static final long serialVersionUID = 749360940290141180L;

    private Long rightId;
    private String pathMethod;
    private Integer rightType;

    /**
     * 是否租户内公共权限
     */
    private String ifCommon;

    /**
     * 权限范围
     * 1、范围类型，1=服务网点
     * 2、'是否有全部权限'
     * 3、'是否有所在组织权限'
     * 4、'是否有所在组织的下级权限'
     */
    private Short scopeType;
    private String hasAllScope;
    private String hasOwnScope;
    private String hasOwnLowerScope;
}
