package com.b5m.core.entity;

/**
 * @description: 消息状态码定义
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: Leo.li
 * @version: 1.0
 * @createdate: 16-4-8
 * <p/>
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * 16-4-8       Leo.li          1.0             TODO
 */
public enum MsgCode {
    OK("2000"),

    CLIENT_ERROR("40000000"),
    CLIENT_PARAMETER_ERROR("40000001"),
    CLIENT_REQUEST_TOO_FREQUENTLY("40000002"),
    CLIENT_REQUEST_TIMEOUT("40000003"),
    CLIENT_UNAUTHORIZED("40000004"),
    CLIENT_DUPLICATE_REQUEST("40000005"),
    CLIENT_EMPTY_PARAMETER("40000006"),
    CLIENT_ILLEGAL_PARAMETER("40000007"),
    CLIENT_DATA_NOT_EXISTS("40000008"),

    SERVER_INTERNAL_ERROR("50000000"),
    SERVER_IS_BUSY("50000001"),
    SERVER_HANDLE_ERROR("50000002");

    private final String value;
    private final String descr;

    private MsgCode(String value) {
        this.value = value;
        this.descr = this.name();
    }

    private MsgCode(String value, String descr) {
        this.value = value;
        this.descr = descr;
    }

    public final String value(){
        return value;
    }

    public final String descr(){
        return descr;
    }

    public static MsgCode parse(String value){
        for(MsgCode code : values()){
            if(code.value.equals(value)){
                return code;
            }
        }
        return null;
    }

    public static MsgCode parse(Integer value){
        return value == null ? null : parse(value.toString());
    }

    public static MsgCode parse(int value){
        return parse(Integer.toString(value));
    }

}