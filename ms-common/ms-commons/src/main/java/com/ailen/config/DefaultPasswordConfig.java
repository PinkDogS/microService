package com.ailen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码工具类
 * uaa与gateway与uas共用
 *
 * @author anshare
 *
 */
public class DefaultPasswordConfig {

    /**
     * 装配BCryptPasswordEncoder用户密码的匹配
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
//        return new MessageDigestPasswordEncoder("SHA-256");
        return new BCryptPasswordEncoder();
    }
}
