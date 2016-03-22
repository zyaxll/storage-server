package com.b5m.storage.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作工具类
 * @author lucky.liu
 * @version v2.0.0 2014年9月26日 上午10:49:51
 */
public abstract class FileUtils {

    private FileUtils(){}

    public static void close(Closeable c){
        if(c != null){
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }
    
    /**
     * 创建目录，包括父目录，如果文件不是目录，则创建父目录
     * @param file 指定文件或目录
     * @return
     */
    public static boolean mkdirs(File file){
        if(file == null){
            return false;
        }
        if(!file.isDirectory()){
            file = file.getParentFile();
        }
        return file.mkdirs();
    }
    
    /**
     * 以UTF-8编码读取文本数据
     * @param filepath 文本文件路径
     * @return 返回文本文件内容字符串
     * @throws IOException
     */
    public static String readFromFile(String filepath) throws IOException{
        return readFromFile(filepath, "UTF-8");
    }
    
    /**
     * 以指定编码读取文本数据
     * @param filepath 文本文件路径
     * @param charset 字符编码
     * @return 返回文本文件内容字符串
     * @throws IOException
     */
    public static String readFromFile(String filepath, String charset) throws IOException{
        File file = new File(filepath);
        if(file.isDirectory()){
            throw new IOException("文件是目录");
        }
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(file);
            return readBytes(fis, charset);
        }finally{
            close(fis);
        }
    }
    
    /**
     * 从指定流中读取utf-8字符串
     * @param is 数据流
     * @return 返回流所表示的字符串
     * @throws IOException
     */
    public static String readBytes(InputStream is) throws IOException{
        return readBytes(is, "UTF-8");
    }
    
    /**
     * 按照指定编码从指定流中读取字符串
     * @param is 数据流
     * @param charset 字符串编码
     * @return
     * @throws IOException
     */
    public static String readBytes(InputStream is, String charset) throws IOException{
        if(is == null) {
            return null;
        }
        byte[] buffer = new byte[128];
        int len;
        ByteArrayOutputStream bos = null;
        try{
            bos = new ByteArrayOutputStream();
            while((len = is.read(buffer)) > 0){
                bos.write(buffer, 0, len);
            }
            bos.flush();
            return bos.toString(charset);
        }finally{
            close(bos);
        }
    }
    
    /**
     * 获取文件名对应的后缀名，无后缀则返回空字符串
     * @param fileName 文件名
     * @return 后缀名
     */
    public static String getFileType(String fileName){
        if(fileName == null || fileName.length() == 0){
            return "";
        }
        int index = fileName.lastIndexOf('.');
        return (index < 0 || index == fileName.length() - 1) ? "" : fileName.substring(index + 1);
    }
    
    /**
     * 将字符串以UTF-8编码写入文件中
     * @param path 文件对应的路径
     * @param data 字符串数据
     * @throws IOException
     */
    public static void writeFile(String path, String data) throws IOException{
        writeFile(path, data, "UTF-8");
    }
    
    /**
     * 将字符串以指定编码写入文件中
     * @param path 文件对应的路径
     * @param data 字符串数据
     * @param charset 字符串编码
     * @throws IOException
     */
    public static void writeFile(String path, String data, String charset) throws IOException{
        File file = new File(path);
        if(file.isDirectory()){
            throw new IOException(path + " is a directory!");
        }
        mkdirs(file);
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            fos.write(data.getBytes(charset));
            fos.flush();
        }finally{
            close(fos);
        }
    }
}
