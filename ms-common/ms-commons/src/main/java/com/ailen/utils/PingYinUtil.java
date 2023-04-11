package com.ailen.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @author anshare
 *
 * @Version 1.0
 **/
public class PingYinUtil {

    // 是否包含字母
    public static final Pattern P_WORD = Pattern.compile(".*[a-zA-z].*");

    /**
     *@Description 根据汉字获取全拼
     *@author anshare
     *@date
     *@Param src
     *@Return String
     **/
    public static String getPingYin(String src) {
        char[] t1 = src.toCharArray();
        String[] t2;
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        //设置拼音大小写 LOWERCASE(小写)  UPPERCASE(大写)
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //设置声调
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        try {
            for (char c : t1) {
                // 判断是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(c, t3);
                    t4.append(t2[0]);
                } else {
                    t4.append(c);
                }
            }
            return t4.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4.toString();
    }

    /**
     *@Description 根据汉字获取首字母
     *@author anshare
     *@date
     *@Param str
     *@Return String
     **/
    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

    /** 输入的全是拼音字母
     *@Description
     *@author anshare
     *@date
     *@Param match
     *@Return Boolean
     **/
    public static Boolean allIsEn(String match){
        if (StringUtils.isEmpty(match)) {
            return false;
        }
        if (!P_WORD.matcher(match).matches()){
            return false;
        }
        return true;
    }
}
