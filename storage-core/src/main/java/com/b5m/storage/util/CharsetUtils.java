/*
 * Copyright (c) 2015. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.b5m.storage.util;

import java.io.UnsupportedEncodingException;

/**
 * //TODO
 *
 * @author lucky.liu
 * @version 2015-12-23 10:51
 * @email liuwb2010@gmail.com
 */
public abstract class CharsetUtils {

    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    // 更新编码顺序能提高编码识别的性能
    private static final String[] CHARSET_NAME_ARRAY = {"GBK", "ISO8859_1", "UTF-8", "GB2312", "BIG5",
        "Unicode", "EUC_KR", "SJIS", "EUC_JP", "ASCII"};

    private CharsetUtils(){}

    /**
     * 获取字符串的编码，该方法只能识别java支持的编码类型 ,字符串过大不建使用此方法
     *
     * @param text
     * @return
     */
    public static String getCharsetName(String text) {
        if (text == null){
            throw new NullPointerException();
        }
        for (String charset : CHARSET_NAME_ARRAY) {
            try {
                if (text.equals(new String(text.getBytes(charset), charset))) {
                    return charset;
                }
            } catch (UnsupportedEncodingException e) {
            }
        }
        return "";
    }

    /**
     * 将字符串的编码转换成GBK<br>
     * 原编码类型必须为java支持的编码
     *
     * @param text
     * @return
     */
    public static String changeCharset(String text) {
        return changeCharset(text, DEFAULT_CHARSET_NAME);
    }

    /**
     * 将字符串的编码转换成指定的编码<br>
     * 原编码类型必须为java支持的编码
     *
     * @param text，转换字符串
     * @param destCharsetName，目标编码
     * @return
     */
    public static String changeCharset(String text, String destCharsetName) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String encode = getCharsetName(text);
        if (destCharsetName.equals(encode))
            return text;
        return changeCharset(text, getCharsetName(text), destCharsetName);
    }

    /**
     * 将字符串的编码转换成指定的编码<br>
     * 原编码类型必须为java支持的编码
     *
     * @param text，转换字符串
     * @param srcCharsetName,源编码
     * @param destCharsetName，目标编码
     * @return
     */
    public static String changeCharset(String text, String srcCharsetName, String destCharsetName) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        try {
            return new String(text.getBytes(srcCharsetName), destCharsetName);
        } catch (UnsupportedEncodingException e) {
            return text;
        }
    }

}
