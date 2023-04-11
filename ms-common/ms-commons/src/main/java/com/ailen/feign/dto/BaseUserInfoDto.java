package com.ailen.feign.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author anshare
 * @Descpription 普通用户信息类
 *
 * @Version 1.0
 **/
@Getter
@Setter
public class BaseUserInfoDto {
    
    /** 用户id **/
    private Long userId;
    
    /** 登录名 **/
    private String loginId;
    
    /** 姓名 **/
    private String userName;
    
    /** 手机号 **/
    private String mobile;
    
    /** 昵称 **/
    private String nickname;
    
    /** 1=正常 2=失效 **/
    private String status;
    
    /** 注册时间 **/
    private Date regTime;
    
    /** 1=男 2=女 空=不知道 **/
    private String sex;
    
    /** 个性签名 **/
    private String signature;
    
    /** 国家 **/
    private String country;
    
    /** 省份代码 **/
    private String province;
    
    /** 地市代码 **/
    private String city;
    
    /** 区县代码 **/
    private String district;
    
    
}
