package com.b5m.storage.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序例化工具类
 * @author lucky.liu
 * @version v2.0.0 2014年9月26日 下午2:15:23
 */
public abstract class SerialUtils {

	private SerialUtils(){}

    /**
     * 将类实例序例化到文件中
     * @param obj 类实例
     * @param path 文件路径
     * @throws IOException
     */
	public static void writeObject(Object obj,String path) throws IOException{
	    writeObject(obj, new File(path));
	}
	
	/**
     * 将类实例序例化到文件中
     * @param obj 类实例
     * @param file 文件对象
     * @throws IOException
     */
	public static void writeObject(Object obj,File file) throws IOException{
	    FileOutputStream fos=null;
	    ObjectOutputStream oos=null;
	    try{
	        fos=new FileOutputStream(file);
	        oos=new ObjectOutputStream(fos);
	        oos.writeObject(obj);
	        oos.flush();
	    }finally{
	        if(fos!=null)fos.close();
	        if(oos!=null)oos.close();
	    }
	}
	
	/**
	 * 从文件中序例化读取对象实例
	 * @param path 文件路径
	 * @return 对象实例
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
    public static <T> T readObject(String path) throws ClassNotFoundException,IOException {
	    return readObject(new File(path));
	}
	
    /**
     * 从文件中序例化读取对象实例
     * @param file 文件实例
     * @return 对象实例
     * @throws ClassNotFoundException
     * @throws IOException
     */
	@SuppressWarnings("unchecked")
	public static <T> T readObject(File file) throws ClassNotFoundException,IOException {
	    FileInputStream fis=null;
	    ObjectInputStream ois=null;
	    try{
	        fis=new FileInputStream(file);
	        ois=new ObjectInputStream(fis);
	        Object obj=ois.readObject();
	        return (T)obj;
	    }finally{
	        if(fis!=null)fis.close();
	        if(ois!=null)ois.close();
	    }
	}
	
}
