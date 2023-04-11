package com.ailen.feign.service;

import com.anshare.base.common.feign.service.fallback.RightFeignServiceFallbackFactory;
import com.anshare.base.common.model.Result;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;
import java.util.Map;

@FeignClient(name = "base-uas", fallbackFactory = RightFeignServiceFallbackFactory.class)
public interface RightFeignService {

    /**
     * 人员与角色列表映射
     *
     * @param corpId
     * @return
     * @author anshare
     * 
     **/
    @RequestMapping(value = "/sys-role-user/feign/map/{corpId}", method = RequestMethod.GET)
    Result<Map<Long, Object>> mapUserIdAndRoleList(@PathVariable("corpId") Long corpId);

    /**
     * 人员与角色映射
     *
     * @param jsonFilter
     * @return
     * @author anshare
     * 
     **/
    @RequestMapping(value = "/sys-role-user/feign/map/users", method = RequestMethod.POST, headers = {"content-type=application/json"})
    Result<Map<Long, String>> mapUserIdAndRoleNames(@RequestBody String jsonFilter);

    /**
     * 添加系统角色
     *
     * @param corpId
     * @param userId
     * @return
     * @author anshare
     * 
     **/
    @RequestMapping(value = "/sys-role/feign/sys/add", method = RequestMethod.POST)
    Result addSysRole(@RequestParam("corpId") Long corpId,
                      @RequestParam("userId") Long userId);

    /**
     * 获得具有某权限的人员列表
     *
     * @param corpId
     * @param rightId
     * @return
     **/
    @RequestMapping(value = "/user-right/feign/listUserByRightId", method = RequestMethod.GET)
    Result<List<Long>> listUserByRightId(@RequestParam("corpId") Long corpId,
                                         @RequestParam("rightId") Long rightId);

    /**
     * 获得具有某权限的人员列表，排除系统管理员用户
     *
     * @param corpId
     * @param rightId
     * @return
     **/
    @RequestMapping(value = "/user-right/feign/listUserByRightIdNoSysUser", method = RequestMethod.GET)
    Result<List<Long>> listUserByRightIdNoSysUser(@RequestParam("corpId") Long corpId,
                                                  @RequestParam("rightId") Long rightId);

    /**
     * 获取具有某权限的人员列表，排除隐藏人员
     *
     * @param corpId
     * @param rightId
     * @return
     */
    @RequestMapping(value = "/user-right/feign/listUserByRightIdNoHidden", method = RequestMethod.GET)
    Result<List<Long>> listUserByRightIdNoHidden(@RequestParam("corpId") Long corpId,
                                                 @RequestParam("rightId") Long rightId);

    /**
     * 初始化用户所有角色的redis
     *
     * @param userId
     * @return
     * @author anshare
     * @date
     **/
    @RequestMapping(value = "/sys-role-user/feign/redis-init/all", method = RequestMethod.POST)
    Result initUserRoleRedis(@RequestParam("userId") Long userId);

    /**
     * 获取具有某权限所有范围的人员列表，排除隐藏人员
     *
     * @param corpId
     * @param rightId
     * @return
     */
    @RequestMapping(value = "/user-right/feign/listUserByRightIdAllScope", method = RequestMethod.POST)
    Result<List<Long>> listUserByRightIdAllAcope(@RequestParam("corpId") Long corpId,
                                                 @RequestParam("rightId") Long rightId);

}
