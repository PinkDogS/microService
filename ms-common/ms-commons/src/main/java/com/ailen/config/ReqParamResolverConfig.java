package com.ailen.config;

import com.anshare.base.common.resolver.ReqParamArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *
 * @author anshare
 * @version 1.0
 *
 **/
public class ReqParamResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new ReqParamArgumentResolver());
    }
}
