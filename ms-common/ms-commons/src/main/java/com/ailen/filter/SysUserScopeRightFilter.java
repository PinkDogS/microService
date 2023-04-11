package com.ailen.filter;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 人员范围权限
 *
 * @author anshare
 * @version 1.0
 * @date
 */
@Getter
@Setter
public class SysUserScopeRightFilter {

    // 权限编号
    private Long rightId;

    // 人员编号
    private Long userId;

    /**
     * 企业编号 默认 0
     */
    private Long corpId = 0L;

    // 人员编号列表
    private List<Long> userIdList;

    /**
     * 范围类型
     * 参考 RightScopeTypeEnum
     */
    private Integer scopeType;

}
