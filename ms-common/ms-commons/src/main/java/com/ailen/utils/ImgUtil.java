package com.ailen.utils;

import cn.hutool.core.io.FileUtil;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author anshare
 * @date
 * @Version 1.0
 **/
public class ImgUtil {


    /**
     * 重新生成图片宽、高
     *
     * @param srcPath   图片路径
     * @param destPath  新生成的图片路径
     * @param newWith   新的宽度
     * @param newHeight 新的高度
     * @param forceSize 是否强制使用指定宽、高,false:会保持原图片宽高比例约束
     * @return
     * @throws IOException
     */
    public static boolean resizeImage(String srcPath, String destPath, int newWith, int newHeight, boolean forceSize) throws IOException {
        if (forceSize) {
            Thumbnails.of(srcPath).forceSize(newWith, newHeight).toFile(destPath);
        } else {
            Thumbnails.of(srcPath).width(newWith).height(newHeight).toFile(destPath);
        }
        return true;
    }

    /**
     * 压缩图片文件
     * 必要时对超过大小的图片进行压缩（缩小分辨率）。注意目标图片格式不能是png，最好使用jpg。
     *
     * @param srcPath    源文件路径
     * @param destPath   目标文件路径
     * @param bytesLimit 目标文件大小限制（字节）,在小尺寸时可能超出。
     * @throws IOException
     */
    public static void compressFile(String srcPath, String destPath, long bytesLimit) throws IOException {
        File srcFile = new File(srcPath);
        File destFile = FileUtil.newFile(destPath);
        long idx = 2;
        // 检查文件是否存在
        if (srcFile.exists()) {
            // 判断输出的文件夹路径是否存在，不存在则创建
            if (!destFile.getParentFile().exists()) {
                destFile.getParentFile().mkdirs();
                System.out.println("make dest dir:" + destFile.getParentFile().getAbsolutePath());
            }
            long len = srcFile.length();
            if (len > bytesLimit) {
                idx = (len + bytesLimit - 1) / bytesLimit;
                double r = Math.sqrt(((double) len) / bytesLimit + 0.2);
                idx = Math.round(r * 2 + 0.2);
                if (idx < 3) {
                    idx = 3;
                }
            } else {
                idx = 2;
            }
        } else {
            System.err.println("image file not exists:" + srcFile.getAbsolutePath());
        }
        // 获取原文件高度和宽度
        BufferedImage src = null;
        int results[] = {0, 0};

        src = ImageIO.read(srcFile);
        if (src == null) {
            System.err.println("image format not supported:" + srcFile.getAbsolutePath());
        }
        results[0] = src.getWidth(null); // 得到源图宽
        results[1] = src.getHeight(null); // 得到源图高

        String srcFormat = srcFile.getName().substring(srcFile.getName().lastIndexOf(".") + 1);
        String destFormat = destPath.substring(destPath.lastIndexOf(".") + 1);
        int heightDest = 0;
        int widthDest = 0;
        if (results[0] == 0 || results[1] == 0) {
            System.err.println("image size error:" + srcFile.getAbsolutePath());
        } else if (idx > 2) {
            widthDest = results[0] * 2 / (int) idx;
            heightDest = results[1] * 2 / (int) idx;
        } else {
            //直接复制文件
            if (srcFormat.equalsIgnoreCase(destFormat)) {
                FileUtil.copyFile(srcFile, destFile);
            }
            widthDest = results[0];
            heightDest = results[1];
        }
        if (IntUtil.isNotZero(widthDest) && IntUtil.isNotZero(heightDest)) {
            resizeImage(srcPath, destPath, widthDest, heightDest, false);
        }
    }

}
