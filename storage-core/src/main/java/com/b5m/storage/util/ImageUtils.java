package com.b5m.storage.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * 图片工具类
 * @author lucky.liu
 * @version v2.0.0 2014年11月5日 下午5:16:00
 */
public abstract class ImageUtils {

    private ImageUtils(){}

    /**
     * 判断url对应的图片长宽是否匹配
     * @param url 图片url链接
     * @param width 指定宽度
     * @param height 指定高度
     * @return
     */
    public static boolean isMeasure(String url, int width, int height){
        if(width < 0 || height < 0){
            return false;
        }
        try {
            BufferedImage image = ImageIO.read(new URL(url));
            return image != null && image.getWidth() == width && image.getHeight() == height;
        } catch (IOException e) {
            return false;
        } catch (Exception e){
            return false;
        }
    }
    
    /**
     * 判断字节数组对应的图片长宽是否匹配
     * @param buf 图片字节数组
     * @param width 指定宽度
     * @param height 指定高度
     * @return
     */
    public static boolean isMeasure(byte[] buf, int width, int height){
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(buf);
            return isMeasure(bis, width, height);
        } finally{
            if(bis != null){
                try {
                    bis.close();
                } catch (IOException e) {
                }
            }
        }
    }
    
    /**
     * 判断数据流对应的图片长宽是否匹配
     * @param is 图片数据流
     * @param width 指定宽度
     * @param height 指定高度
     * @return
     */
    public static boolean isMeasure(InputStream is, int width, int height){
        try {
            BufferedImage image = ImageIO.read(is);
            return image != null && image.getWidth() == width && image.getHeight() == height;
        } catch (IOException e) {
            return false;
        } catch (Exception e){
            return false;
        }
    }
    
}
