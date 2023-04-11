package com.ailen.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 角色
 *
 * @author anshare
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Role extends SuperEntity implements Serializable {
    private static final long serialVersionUID = 4497149010220586111L;
    private String roleId;
    private String roleName;
    private List<Long> userId;
    private Short sysType;

    private List<Integer> appId;

    private List<Right> rightList;
}
