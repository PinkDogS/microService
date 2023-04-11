package com.ailen.utils;

import java.util.regex.Pattern;

public class MobileUtil {

    public static boolean isMobile(String mobile) {
        String regEx = "^1[3456789]\\d{9}$";
        if (!Pattern.matches(regEx, mobile)) {
            return false;
        }
        return true;
    }
}
