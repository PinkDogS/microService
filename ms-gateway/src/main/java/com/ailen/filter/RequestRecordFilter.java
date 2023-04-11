package com.ailen.filter;

import cn.hutool.json.JSONObject;
import com.ailen.model.LoginAppUser;
import com.ailen.util.IPUtil;
import com.ailen.utils.KeyUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * 公共请求参数过滤器
 *
 * @author ailen
 * @version 1.0
 *
 **/
@Slf4j
@Component
//实现全局过滤器对请求进行处理，ordered给过滤器优先级
public class RequestRecordFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        获取请求
        ServerHttpRequest request = exchange.getRequest();

        URI requestUri = request.getURI();
        //只记录 http 请求(包含 https)
        String schema = requestUri.getScheme();

        log.info("请求url:{}, 请求方法:{}, 请求地址:{}",
                request.getURI(), request.getMethod(), IPUtil.getIpAddr(request));
//        getContext获取保存在本地线程（threadLocal）上的上下文信息对象
//        getAuthentication获取当前认证了的 principal(当事人),或者 request token (令牌)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
            String clientId = oauth2Authentication.getOAuth2Request().getClientId();

            log.info("当前 clientId:{}", clientId);

            if (oauth2Authentication.getUserAuthentication() != null) {
                LoginAppUser loginAppUser = (LoginAppUser) oauth2Authentication.getPrincipal();
                Long userId = loginAppUser.getUserId();
                log.info("用户 userId:{}", userId);
            }
        }
        /**
         * 如果请求不是http、https的请求，直接放行，执行后面的过滤器
         */
        if ((!"http".equalsIgnoreCase(schema) && !"https".equalsIgnoreCase(schema))) {
            return chain.filter(exchange);
        }

        String commonParamJson = request.getHeaders().getFirst("Common-Params");
        JSONObject jsonObject = new JSONObject(commonParamJson);
        String txId = KeyUtil.getIdStr();
        jsonObject.put("txId", txId);
        String json = JSON.toJSONString(jsonObject);
        log.info("公共参数---commonParams:{},txId:{}", commonParamJson, txId);
        request.mutate()
                .header("x-common-param-header", json)
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return -2;
    }

}
