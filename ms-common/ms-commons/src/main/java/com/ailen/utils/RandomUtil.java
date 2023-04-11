package com.ailen.utils;

import cn.hutool.core.lang.Assert;

import java.security.SecureRandom;

/**
 * random工具类
 * @author anshare
 * @date
 **/
public class RandomUtil extends SecureRandom {

    public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    /**
     * 产生随机数字字符串
     * @param number 数字位数
     * @return
     */
    public static String getRandomNum(Integer number){
        Assert.notNull(number,"number 不能为NULL");
        String result="";
        SecureRandom secureRandom = new SecureRandom();
        for (Integer i = 0 ; i < number; i++)
        {
            result += secureRandom.nextInt(10);
        }
        return result;
    }

    /**
    * @Description 获取随机字符串
    * @param length
    * @Return java.lang.String
    * @author anshare
    * @date
    */
    public static String getRandomString(Integer length){
        Assert.notNull(length,"length 不能为NULL");
        String result="";
        SecureRandom secureRandom = new SecureRandom();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(secureRandom.nextInt(SOURCES.length()));
        }
        return new String(text);
    }
}
