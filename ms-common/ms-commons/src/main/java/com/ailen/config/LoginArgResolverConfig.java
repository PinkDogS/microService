package com.ailen.config;

import com.anshare.base.common.feign.service.AuthFeignService;
import com.anshare.base.common.feign.service.UserFeignService;
import com.anshare.base.common.resolver.ClientArgumentResolver;
import com.anshare.base.common.resolver.TokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 公共配置类, 一些公共工具配置
 *
 * @author anshare
 * 
 */
public class LoginArgResolverConfig implements WebMvcConfigurer {

//    @Lazy
//    @Autowired
//    private UserProvider userProvider;

    @Lazy
    @Autowired
    private AuthFeignService authFeignService;

    @Lazy
    @Autowired
    private UserFeignService userFeignService;

    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
//        argumentResolvers.add(new TokenArgumentResolver(userProvider));
        argumentResolvers.add(new TokenArgumentResolver(userFeignService));
        //注入应用信息
        argumentResolvers.add(new ClientArgumentResolver(authFeignService));
    }
}
