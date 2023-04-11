package com.ailen.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 *个人基本信息
 * @author anshare
 * @date
**/
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uas_user_info")
public class UserInfo extends Model<UserInfo> implements Serializable {

    private static final long serialVersionUID = 7830183740515532178L;

    @TableId("userid")
    /** 用户id **/
    private Long userId;

    /** 手机号 **/
    private String mobile;

    /** 昵称 **/
    private String nickname;

    /** 1=正常 2=失效 **/
    private String status;

    @TableField("regtime")
    /** 注册时间 **/
    private Date regTime;

    /** 1=男 2=女 空=不知道 **/
    private String sex;

    /** 国家 **/
    private String country;

    /** 省份代码 **/
    private String province;

    /** 地市代码 **/
    private String city;

    /** 区县代码 **/
    private String district;

    @TableField("faceimg")
    /** 小头像文件ID **/
    private Long faceImg;

    @TableField("faceimgbig")
    /** 大头像文件ID **/
    private Long faceImgBig;

    /** 是否实名认证 1=是 **/
    private String verified;

    /** 个性签名 **/
    private String signature;

    @TableField(exist = false)
    private List<Role> roles;
    @TableField(exist = false)
    private String roleId;

    /** 姓名 **/
    @TableField("username")
    private String userName;

    /** 身份证姓名 **/
    @TableField(exist = false)
    private String realName;


    @TableField("passwd")
    /** 密码 **/
    private String passwd;

    @TableField("logonid")
    /** 登录名 **/
    private String loginId;

    @TableField(exist = false)
    private List<Long> roleIdList;

    @TableField(exist = false)
    private List<Long> orgIdList;

    //TODO 所属组织 待添加
    /**所属组织ID**/
    @TableField(exist = false)
    private Long ownGroupId;

    @TableField("creatorid")
    /** 创建者ID **/
    private Long creatorId;

    @TableField("createtime")
    /** 创建时间 **/
    private Date createTime;
}
