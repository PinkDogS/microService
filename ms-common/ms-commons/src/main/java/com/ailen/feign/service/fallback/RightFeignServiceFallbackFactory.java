package com.ailen.feign.service.fallback;

import com.anshare.base.common.feign.service.RightFeignService;
import com.anshare.base.common.model.Result;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RightFeignServiceFallbackFactory implements FallbackFactory<RightFeignService> {
    @Override
    public RightFeignService create(Throwable throwable) {

        return new RightFeignService() {

            /**
             * 人员与角色列表映射
             *
             * @param corpId
             * @return
             * @author anshare
             *
             **/
            @Override
            public Result<Map<Long, Object>> mapUserIdAndRoleList(Long corpId) {
                return null;
            }

            /**
             * 人员与角色映射
             *
             * @param jsonFilter
             * @return
             * @author anshare
             *
             **/
            @Override
            public Result<Map<Long, String>> mapUserIdAndRoleNames(String jsonFilter) {
                return null;
            }


            /**
             * 添加系统角色
             *
             * @param corpId
             * @param userId
             * @return
             * @author anshare
             *
             **/
            @Override
            public Result addSysRole(Long corpId, Long userId) {
                return null;
            }

            /**
             * 获得具有某权限的人员列表
             *
             * @param corpId
             * @param rightId
             * @return
             **/
            @Override
            public Result<List<Long>> listUserByRightId(Long corpId, Long rightId) {
                return null;
            }

            /**
             * 获得具有某权限的人员列表，排除系统管理员用户
             *
             * @param corpId
             * @param rightId
             * @return
             **/
            @Override
            public Result<List<Long>> listUserByRightIdNoSysUser(Long corpId, Long rightId) {
                return null;
            }

            /**
             * 获取具有某权限的人员列表，排除隐藏人员
             *
             * @param corpId
             * @param rightId
             * @return
             */
            @Override
            public Result<List<Long>> listUserByRightIdNoHidden(Long corpId, Long rightId) {
                return null;
            }

            /**
             * 初始化用户角色的redis
             *
             * @param userId
             * @return
             * @author anshare
             * @date
             **/
            @Override
            public Result initUserRoleRedis(Long userId) {
                return null;
            }

            /**
             * 获取具有某权限的所有范围权限的人员列表（排除隐藏人员）
             *
             * @param corpId
             * @param rightId
             * @return
             */
            @Override
            public Result<List<Long>> listUserByRightIdAllAcope(Long corpId, Long rightId) {
                return null;
            }

        };
    }
}
