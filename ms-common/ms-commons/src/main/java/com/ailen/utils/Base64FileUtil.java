package com.ailen.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;

import java.io.*;

/**
 * @author anshare
 * @date
 */
public class Base64FileUtil {

    /**
     * BASE64转File
     *
     * @param base64
     * @return
     * @author anshare
     * @date
     */
    public static File base64ToFile(String base64) {
        if (StrUtil.isBlank(base64)) {
            return null;
        }
        byte[] buff = Base64.decode(base64);
        File file = null;
        FileOutputStream fout = null;
        try {
            file = File.createTempFile("tmp", null);
            fout = new FileOutputStream(file);
            fout.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 文件转BASE64
     *
     * @param file
     * @return
     * @author anshare
     * @date
     */
    public static String fileToBase64(File file) {
        String base64 = null;
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
            byte[] buff = new byte[fin.available()];
            fin.read(buff);
            base64 = Base64.encode(buff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }
}
