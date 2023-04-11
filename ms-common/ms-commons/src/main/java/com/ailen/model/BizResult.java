package com.ailen.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther CK
 * @date
 */
@Setter
@Getter
public class BizResult {
    /**
     * success: 业务成功
     * fail: 业务异常
     */
    private String result;

    /**
     * 业务码
     */
    private Integer code;

    /**
     * 详细信息
     */
    private String message;
}
