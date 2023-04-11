package com.ailen.enums;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限范围类型枚举类
 *
 * @author anshare
 * @version 1.0
 * @date
 */
public enum RightScopeTypeEnum {

    ORG(10, "机构"),
    SUPERVISED_ORG(20, "监督单位");


    /**
     * 类型编码
     **/
    private Integer code;
    /**
     * 类型名称
     **/
    private String name;

    RightScopeTypeEnum(Integer code, String name) {
        this.name = name;
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据code获得name
     *
     * @param code
     * @return
     **/
    public static String getNameByCode(Integer code) {
        for (RightScopeTypeEnum rightScopeTypeEnum : RightScopeTypeEnum.values()) {
            if (rightScopeTypeEnum.getCode().equals(code)) {
                return rightScopeTypeEnum.getName();
            }
        }
        return null;
    }

    /**
     * 获取所有范围类型
     *
     * @return
     */
    public static List<Integer> listScopeType() {
        return Arrays.stream(RightScopeTypeEnum.values())
                .map(rightScopeTypeEnum -> rightScopeTypeEnum.getCode()).collect(Collectors.toList());
    }
}
