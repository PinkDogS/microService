package com.ailen.feign.service.fallback;

import com.anshare.base.common.dto.SysLogDto;
import com.anshare.base.common.feign.dto.BaseUserInfoDto;
import com.anshare.base.common.feign.service.UserFeignService;
import com.anshare.base.common.model.LoginAppUser;
import com.anshare.base.common.model.Result;
import com.anshare.base.common.model.UserInfo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * userService降级工场
 *
 * @author anshare
 *
 */
@Slf4j
@Component
public class UserFeignServiceFallbackFactory implements FallbackFactory<UserFeignService> {
    @Override
    public UserFeignService create(Throwable throwable) {
        return new UserFeignService() {

            /**
             * 通过手机号登录
             *
             * @param mobile
             * @return
             * @author anshare
             *
             **/
            @Override
            public LoginAppUser logonByMobile(String mobile) {
                return null;
            }

            /**
             * 通过logonId查询用户信息
             *
             * @param logonId
             * @return
             */
            @Override
            public Result<LoginAppUser> logonByLogonId(String logonId) {
                return null;
            }

            @Override
            public LoginAppUser logonByWxId(String openId) {
                log.error("通过openId查询用户异常:{}");
                return null;
            }

            /**
             * 获得登陆用户信息
             *
             * @param userId
             */
            @Override
            public Result<UserInfo> findLoginUser(Long userId) {
                return null;
            }

            /**
             * 根据idList获得企业ID与名称映射
             *
             * @param corpIdList
             * @return
             */
            @Override
            public Result<Map<Long, String>> mapCorpIdAndNameByCorpIdList(List<Long> corpIdList) {
                return null;
            }


            @Override
            public Result<Map<Long, String>> mapCorpIdAndNameByJsonCorpIds(List<Long> corpIdList) {
                return null;
            }

            @Override
            public String getOpenidByUserid(Long userId) {
                return null;
            }

            /**
             * 保存登录成功日志
             *
             * @param userLog
             * @return
             * @author anshare
             * @date
             **/
            @Override
            public Result saveLogonSuccessLog(String userLog) {
                return null;
            }

            /**
             * 保存登录成功日志
             *
             * @param userLog
             * @return
             * @author anshare
             * @date
             **/
            @Override
            public Result saveAutoLogonSuccessLog(String userLog) {
                return null;
            }

            /**
             * 保存登录密码错误日志
             *
             * @param userLog
             * @return
             * @author anshare
             * @date
             **/
            @Override
            public Result saveLogonPwdFailLog(String userLog) {
                return null;
            }

            /**
             * 保存登出日志
             *
             * @param userLog
             * @return
             * @author anshare
             * @date
             **/
            @Override
            public Result saveLogoutLog(String userLog) {
                return null;
            }
    
            @Override
            public Result saveSysLog(SysLogDto sysLogDto) {
                return null;
            }
    
            @Override
            public Result<Map<Long, String>> mapUserIdAndNameByUserIdList(List<Long> idList) {
                return null;
            }
    
            @Override
            public Result<Map<Long, BaseUserInfoDto>> mapUserIdAndUserInfoByUserIdList(List<Long> idList) {
                return null;
            }
    
    
        };
    }
}
