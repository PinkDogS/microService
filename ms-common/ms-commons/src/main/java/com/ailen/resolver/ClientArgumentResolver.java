package com.ailen.resolver;

import cn.hutool.core.util.StrUtil;
import com.anshare.base.common.annotation.LoginClient;
import com.anshare.base.common.constant.SecurityConstants;
import com.anshare.base.common.feign.service.AuthFeignService;
import com.anshare.base.common.model.OauthClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Token转化User
 *
 * @author anshare
 *
 */
@Slf4j
public class ClientArgumentResolver implements HandlerMethodArgumentResolver {

    private AuthFeignService authFeignService;

    public ClientArgumentResolver(AuthFeignService authFeignService) {
        this.authFeignService = authFeignService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginClient.class) && (parameter.getParameterType().equals(OauthClient.class) ||
                parameter.getParameterType().equals(String.class));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws IOException {
        LoginClient loginClient = parameter.getParameterAnnotation(LoginClient.class);
        boolean isFull = loginClient.isFull();

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String clientId = request.getHeader(SecurityConstants.CLIENT_ID_HEADER);
        if (StrUtil.isBlank(clientId)) {
            log.warn("resolveArgument error clientId is empty");
        }

        if (isFull) {
            String result = authFeignService.getByClientId(clientId);
            log.debug("oauth-client信息: {}", result);
            OauthClient oauthClient = new ObjectMapper().readValue(result, OauthClient.class);
            return oauthClient;
        } else {
            return clientId;
        }
    }
}
