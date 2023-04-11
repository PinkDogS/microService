package com.ailen.utils;

/**
 * Long工具类
 *
 * @author anshare
 * @version 1.0
 * 
 **/
public class LongUtil {

    public static boolean isNotZero(Long number) {
        if (number != null && number > 0L) {
            return true;
        }
        return false;
    }

    public static boolean isZero(Long number) {
        if (number == null || number == 0L) {
            return true;
        }
        return false;
    }

    public static Long null2Zero(Long number) {
        if (number == null) {
            return 0L;
        }
        return number == null ? 0L : number;
    }
}
