package com.b5m.storage.exception;

import java.io.Serializable;

/**
 * 可使用exception-*.properties配置文件自动生成异常信息的异常状态码
 * 
 * @author 丹青生
 *
 * @date 2015-8-20
 */
public class StatusCode implements Serializable {

    private static final long serialVersionUID = 968330307493536297L;
    
    public final static String SUCCESS_CODE = "2000";
	public final static StatusCode SUCCESS = new StatusCode(SUCCESS_CODE,"Success");

//	服务器常用错误码
	public final static String SERVER_ERROR_CODE = "50000000000";
	public final static StatusCode SERVER_ERROR = new StatusCode(SERVER_ERROR_CODE,"Server Inner Error");

	public final static String SERVER_ERROR_BUSY_CODE = "50000000001";
	public final static StatusCode SERVER_ERROR_BUSY = new StatusCode(SERVER_ERROR_BUSY_CODE,"Server Busy");


//	客户端常用错误码
	public static final String CLIENT_EEROR_CODE = "40000000000";
	public static final StatusCode CLIENT_EEROR = new StatusCode(CLIENT_EEROR_CODE,"Client Error");

	public static final String CLIENT_EEROR_PARAMETER_CODE = "40000000001";
	public static final StatusCode CLIENT_EEROR_PARAMETER = new StatusCode(CLIENT_EEROR_PARAMETER_CODE,"Unknown Parameters");

	public static final String CLIENT_EEROR_ACCESS_TOO_FREQUENTLY_CODE = "40000000002";
	public static final StatusCode CLIENT_EEROR_ACCESS_TOO_FREQUENTLY = new StatusCode(CLIENT_EEROR_ACCESS_TOO_FREQUENTLY_CODE,"Access Too Frequently");

	public static final String CLIENT_EEROR_ACCESS_TIMEOUT = "40000000003";
	public static final StatusCode CLIENT_EEROR_ACCESS_TIMEOUT_PARAMETER = new StatusCode(CLIENT_EEROR_ACCESS_TIMEOUT,"Access Timeout");

	public static final String CLIENT_EEROR_ACCESS_DENY_CODE = "40000000004";
	public static final StatusCode CLIENT_EEROR_ACCESS_DENY = new StatusCode(CLIENT_EEROR_ACCESS_DENY_CODE,"Access Deny");

	public static final String CLIENT_EEROR_RESUBMIT_CODE = "40000000005";
	public static final StatusCode CLIENT_EEROR_RESUBMIT = new StatusCode(CLIENT_EEROR_RESUBMIT_CODE,"Already Submitted");


	private String code;
	private String message;
	
	public StatusCode(String code){
		this(code, convert2Message(code, code));
	}
	
	public StatusCode(String code, String message){
		this.code = code == null ? SERVER_ERROR_CODE : code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	public String toString(){
		return code + "(" + message + ")";
	}
	
	/**
	 * 异常消息转换 
	 * @param code
	 * @param defaultMessage
	 * @return
	 */
	private static String convert2Message(String code, String defaultMessage) {
		String message = null;//ExceptionMessageConfigure.getMessage(code);
		return message == null ? defaultMessage : message;
	}
}
