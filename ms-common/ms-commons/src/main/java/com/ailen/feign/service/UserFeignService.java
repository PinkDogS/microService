package com.ailen.feign.service;

import com.anshare.base.common.dto.SysLogDto;
import com.anshare.base.common.feign.dto.BaseUserInfoDto;
import com.anshare.base.common.feign.service.fallback.UserFeignServiceFallbackFactory;
import com.anshare.base.common.model.LoginAppUser;
import com.anshare.base.common.model.Result;
import com.anshare.base.common.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

/**
 * @author anshare
 * 
 */
@FeignClient(name = "base-uas", fallbackFactory = UserFeignServiceFallbackFactory.class)
public interface UserFeignService {

    /**
     * 通过手机号登录
     *
     * @param mobile
     * @return
     * @author anshare
     * 
     **/
    @RequestMapping(value = "/account/user/mobile/logon", params = "mobile", method = RequestMethod.GET)
    LoginAppUser logonByMobile(@RequestParam("mobile") String mobile);

    /**
     * 通过logonId查询用户信息
     *
     * @param logonId
     * @return
     */
    @RequestMapping(value = "/account/user/logon", params = "logonId", method = RequestMethod.GET)
    Result<LoginAppUser> logonByLogonId(@RequestParam("logonId") String logonId);


    @RequestMapping(value = "/account/user/wx/logon", params = "openId", method = RequestMethod.GET)
    LoginAppUser logonByWxId(@RequestParam("openId") String openId);

    /**
     * 获得登陆用户信息
     */
    @GetMapping(value = "/user-info/feign/loginUser")
    Result<UserInfo> findLoginUser(@RequestParam("userId") Long userId);

    /**
     * 根据idList获得企业ID与名称映射
     *
     * @param corpIdList
     * @return
     */
    @RequestMapping(value = "/corp-registry/feign/mapByCorpIdList", method = RequestMethod.POST)
    Result<Map<Long, String>> mapCorpIdAndNameByCorpIdList(@RequestParam("corpIdList") List<Long> corpIdList);

    /**
     * 根据idList获得企业ID与名称映射
     *
     * @param corpIdList
     * @return
     */
    @RequestMapping(value = "/corp-registry/feign/mapByJsonCorpIds", method = RequestMethod.POST)
    Result<Map<Long, String>> mapCorpIdAndNameByJsonCorpIds(@RequestParam("corpIdList") List<Long> corpIdList);
    
    @GetMapping(value = "/wx/feign/openid/{userId}")
    String getOpenidByUserid(@PathVariable("userId") Long userId);
    
    /**
     * 保存登录成功日志
     *
     * @param userLog
     * @return
     * @author anshare
     * @date
     * @deprecated 服务已迁移至SystemFeignService
     **/
    @RequestMapping(value = "/user-logon-log/feign/success/save", method = RequestMethod.POST, headers = {"content-type=application/json"})
    Result saveLogonSuccessLog(@RequestBody String userLog);
    
    /**
     * 保存登录成功日志
     *
     * @param userLog
     * @return
     * @author anshare
     * @date
     * @deprecated 服务已迁移至SystemFeignService
     **/
    @RequestMapping(value = "/user-logon-log/feign/auto-success/save", method = RequestMethod.POST, headers = {"content-type=application/json"})
    Result saveAutoLogonSuccessLog(@RequestBody String userLog);

    /**
     * 保存登录密码错误日志
     *
     * @param userLog
     * @return
     * @author anshare
     * @date
     * @deprecated 服务已迁移至SystemFeignService
     **/
    @RequestMapping(value = "/user-logon-log/feign/pwd-fail/save", method = RequestMethod.POST, headers = {"content-type=application/json"})
    Result saveLogonPwdFailLog(@RequestBody String userLog);

    /**
     * 保存登出日志
     *
     * @param userLog
     * @return
     * @author anshare
     * @date
     * @deprecated 服务已迁移至SystemFeignService
     **/
    @RequestMapping(value = "/user-logon-log/feign/logout/save", method = RequestMethod.POST, headers = {"content-type=application/json"})
    Result saveLogoutLog(@RequestBody String userLog);
    
    /**
     * 保存系统日志
     *
     * @param sysLogDto
     * @return
     * @author anshare
     * @date
     * @deprecated 服务已迁移至SystemFeignService
     **/
    @RequestMapping(value = "/feign/sys-log/add", method = RequestMethod.POST)
    Result saveSysLog(@RequestBody SysLogDto sysLogDto);
    
    /**
     * 人员ID与名称映射
     *
     * @param idList
     * @return
     * @author anshare
     *
     **/
    @PostMapping(value = "/account/feign/mapByUserIdList")
    Result<Map<Long, String>> mapUserIdAndNameByUserIdList(@RequestBody List<Long> idList);
    
    /**
     * 用户名与用户信息映射
     *
     * @param idList
     * @return
     * @author anshare
     * @date
     */
    @PostMapping(value = "/feign/user-info/mapUserIdAndUserInfo")
    Result<Map<Long, BaseUserInfoDto>> mapUserIdAndUserInfoByUserIdList(@RequestBody List<Long> idList);
    
}
