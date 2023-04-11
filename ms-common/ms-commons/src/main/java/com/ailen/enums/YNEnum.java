package com.ailen.enums;

/**
 * 是/否 枚举类
 *
 **/
public enum YNEnum {

    YES(true, "Y"),
    NO(false, "N");

    /**
     * 代码
     */
    private boolean code;

    /**
     * 名称
     */
    private String name;

    YNEnum(boolean code, String name) {
        this.name = name;
        this.code = code;
    }

    public boolean getCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getNameByCode(boolean code) {
        for (YNEnum ynEnum : YNEnum.values()) {
            if (ynEnum.getCode() == code) {
                return ynEnum.getName();
            }
        }
        return null;
    }
}
