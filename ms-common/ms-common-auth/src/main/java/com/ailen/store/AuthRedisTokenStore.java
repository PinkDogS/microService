package com.ailen.store;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 认证服务器使用Redis存取令牌
 * 注意: 需要配置redis参数
 *
 * @author anshare
 * 
 */
@Slf4j
public class AuthRedisTokenStore {

    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Bean
    public TokenStore tokenStore() {
        MyRedisTokenStore redisTokenStore = new MyRedisTokenStore(connectionFactory);
        redisTokenStore.setPrefix("auth:oauth:");
        return redisTokenStore;
    }
}

