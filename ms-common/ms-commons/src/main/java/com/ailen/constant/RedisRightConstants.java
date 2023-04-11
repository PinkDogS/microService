package com.ailen.constant;

/**
 * Redis常量
 *
 * @author anshare
 * @version 1.0
 * @date
 */
public class RedisRightConstants {

    /**
     * 系统公共权限初始化标记
     *
     * @param
     * @return
     * @author anshare
     *
     **/
    public static String getRightCommonInit() {
        return "uas:right-star-url-common-init-flag";
    }

    /**
     * 角色权限初始化标记
     *
     * @param
     * @return
     * @author anshare
     * @date
     **/
    public static String getRightRoleInit() {
        return "uas:right-role-rightList-init-flag";
    }

    /**
     * 带*的用户权限前缀
     *
     * @param
     * @return
     * @author anshare
     *
     **/
    public static String getUserStarKeyPatten() {
        return "uas:right-star-user";
    }

    /**
     * 带*的用户权限前缀
     *
     * @param
     * @return
     * @author anshare
     *
     **/
    public static String getUserUrlKeyPatten() {
        return "uas:right-url-user";
    }

    /**
     * 带*的公共权限
     *
     * @param
     * @return
     * @author anshare
     *
     **/
    public static String getRightStarCommon() {
        return "uas:right-star-common";
    }

    /**
     * 不带*的公共权限
     *
     * @param url 请求路径
     * @return
     * @author anshare
     *
     **/
    public static String getRightUrlCommon(String url) {
        return "uas:right-url-common:" + url;
    }

    /**
     * 用户权限编号
     *
     * @param corpId 企业编号
     * @param userId 用户编号
     * @return
     * @author anshare
     *
     **/
    public static String getRightUserRightId(Long corpId, Long userId) {
        return "uas:right-user-rightId:" + corpId + ":" + userId;
    }

    /**
     * 用户范围权限
     *
     * @param corpId  企业编号
     * @param userId  用户编号
     * @param rightId 权限编号
     * @return
     * @author anshare
     * @date
     **/
    public static String getRightUserScope(Long corpId, Long userId, Long rightId) {
        return "uas:right-user-scope:" + corpId + ":" + userId + ":" + rightId;
    }
    public static String getRightUserScope(String corpId, String userId, String rightId) {
        return "uas:right-user-scope:" + corpId + ":" + userId + ":" + rightId;
    }

    /**
     * 角色权限key
     *
     * @param roleId 角色编号
     * @return
     * @author anshare
     * @date
     **/
    public static String getRoleRightKey(Long roleId) {
        return "uas:right-role-rightList:" + roleId;
    }

    /**
     * 角色权限key
     *
     * @param userId 人员编号
     * @param corpId 企业编号
     * @return
     * @author anshare
     * @date
     **/
    public static String getUserRoleKey(Long userId, Long corpId) {
        return "uas:right-user-roleIdList:" + corpId + ":" + userId;
    }
    public static String getUserRoleKey(String userId, String corpId) {
        return "uas:right-user-roleIdList:" + corpId + ":" + userId;
    }

    /**
     * 企业租户类型key
     *
     * @param corpId
     * @return
     * @author anshare
     * @date
     **/
    public static String getCorpTenantKey(Long corpId) {
        return "uas:corp-tenant:" + corpId;
    }

    /**
     * 租户权限key
     *
     * @param tenant
     * @return
     * @author anshare
     * @date
     **/
    public static String getTenantRightKey(String tenant) {
        return "uas:tenant-right:" + tenant;
    }

    /**
     * 企业系统管理员角色人员key
     *
     * @param corpId
     * @return
     * @author anshare
     * @date
     **/
    public static String getCorpSysRoleUserKey(Long corpId) {
        return "uas:corp-sys-role-user:" + corpId;
    }

    /**
     * 企业租户类型初始化标记
     *
     * @param
     * @return
     * @author anshare
     * @date
     **/
    public static String getCorpTenantInit() {
        return "uas:corp-tenant-init-flag";
    }

    /**
     * 企业租户权限初始化标记
     *
     * @param
     * @return
     * @author anshare
     * @date
     **/
    public static String getTenantRightInit() {
        return "uas:tenant-right-init-flag";
    }

    /**
     * 企业系统管理员角色人员初始化标记
     *
     * @param
     * @return
     * @author anshare
     * @date
     **/
    public static String getCorpSysRoleUserInit() {
        return "uas:corp-sys-role-user-init-flag";
    }
}
