package com.ailen.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * @author anshare
 * 
 */
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OauthClient {

    private String clientId;

    private Long corpId; // 租户信息

    private String additionalInformation = "{}";
}
