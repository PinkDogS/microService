package com.ailen.feign.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 角色权限
 * @author anshare
 * @version 1.0
 *
 */
@Getter
@Setter
public class SysRoleRightDto {

    /**
     * 企业编号
     */
    private Long corpId;

    /**
     * 角色编号
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 权限编号
     */
    private Long rightId;

    /**
     * 权限名称
     */
    private String rightName;

    /**
     * 权限类型
     */
    private String rightType;

    /**
     * 请求路径
     */
    private String uri;

    /**
     * 请求方法
     */
    private String pathMethod;
}
