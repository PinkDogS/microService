package com.ailen.feign.service;

import com.anshare.base.common.dto.SysLogDto;
import com.anshare.base.common.feign.dto.DictItemDto;
import com.anshare.base.common.feign.service.fallback.SystemFeignServiceFallbackFactory;
import com.anshare.base.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author anshare
 * 
 */
@FeignClient(name = "base-uas", fallbackFactory = SystemFeignServiceFallbackFactory.class)
public interface SystemFeignService {
    /**
     * 保存登录成功日志
     *
     * @param userLog
     * @return
     * @author anshare
     * @date
     **/
    @RequestMapping(value = "/user-logon-log/feign/success/save", method = RequestMethod.POST, headers = {"content-type=application/json"})
    Result saveLogonSuccessLog(@RequestBody String userLog);
    
    
    /**
     * 保存自动登录成功日志
     *
     * @param userLog
     * @return
     * @author anshare
     * @date
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
     **/
    @RequestMapping(value = "/feign/sys-log/add", method = RequestMethod.POST)
    Result saveSysLog(@RequestBody SysLogDto sysLogDto);
    
    
    /**
     * 根据字典编码和值查询字典项列表
     *
     * @param
     * @return
     * @author anshare
     * 
     **/
    @PostMapping(value = "/feign/sysDict/listByDictCodeAndItemValueList")
    Result<List<DictItemDto>> listByDictCodeAndItemValueList (@RequestBody List<DictItemDto> list);
    
}
