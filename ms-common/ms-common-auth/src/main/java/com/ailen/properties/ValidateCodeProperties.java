package com.ailen.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码配置
 *
 * @author anshare
 *
 */
@Setter
@Getter
public class ValidateCodeProperties {
    /**
     * 设置认证通时不需要验证码的clientId
     */
    private String[] ignoreClientCode = {};
}
