package com.ailen.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 公共参数
 * @author anshare
 * @version 1.0
 * 
 **/
@Data
public class ReqParam {

    private String version;
    private String coreVersion;
    private Long txId;
    private Integer retryFlag;
    private BigDecimal lon;
    private BigDecimal lat;
    private String deviceId;
    private Integer deviceType;
    private String osVersion;
    private String h5Version;

    private Long corpId;
}
