package com.ailen.feign.service.fallback;

import com.anshare.base.common.dto.SysLogDto;
import com.anshare.base.common.feign.dto.DictItemDto;
import com.anshare.base.common.feign.service.SystemFeignService;
import com.anshare.base.common.model.Result;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * dict
 *
 * @author anshare
 *
 */
@Slf4j
@Component
public class SystemFeignServiceFallbackFactory implements FallbackFactory<SystemFeignService> {
    @Override
    public SystemFeignService create(Throwable throwable) {
        return new SystemFeignService() {
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
            public Result<List<DictItemDto>> listByDictCodeAndItemValueList(List<DictItemDto> list) {
                return null;
            }
            
        };
    }
}
