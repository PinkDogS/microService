package com.ailen.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;


@Slf4j
public class ByteConversion {

    /**
     * 字节数字格式转换
     * @param size
     * @return
     */
    public static String byteConversion(Long size) {
        try {
            if (size == null || size <= 0) {
                return "0";
            }
            final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
            int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
            return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + "" + units[digitGroups];
        } catch (Exception e) {
            log.error("字节数字格式转换失败：" + e.getMessage());
            return null;
        }
    }

}
