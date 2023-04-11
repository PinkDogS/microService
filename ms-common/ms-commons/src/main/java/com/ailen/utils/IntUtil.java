package com.ailen.utils;

/**
 * Int工具类
 *
 * @author anshare
 * @version 1.0
 * 
 **/
public class IntUtil {

    public static boolean isNotZero(Integer number){
        if(number != null && number > 0){
            return true;
        }
        return false;
    }

    public static boolean isZero(Integer number){
        if(number == null || number == 0){
            return true;
        }
        return false;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
