package com.b5m.storage.exception;

/**
 * 异常对象
 * 
 * @author 丹青生
 *
 * @date 2015-8-20
 */
public class B5mException extends RuntimeException {

	private static final long serialVersionUID = -1328224619794339672L;
	
	private String code;
	
	public B5mException(){}
	
	public B5mException(StatusCode code){
		super(code.getMessage());
		this.code = code.getCode();
	}
	
	public B5mException(StatusCode code, Throwable cause){
		super(code.getMessage(), cause);
		this.code = code.getCode();
	}
	
	public B5mException(String message){
		super(message);
		this.code = StatusCode.SERVER_ERROR_CODE;
	}
	
	public B5mException(String message, Throwable cause){
		super(message, cause);
		this.code = StatusCode.SERVER_ERROR_CODE;
	}
	
	public B5mException(Throwable cause){
		super(cause);
	}
	
	public void setCode(StatusCode code) {
	    this.code = code.getCode();
	}
	
	public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
		return code;
	}
	
	public String toString() {
        String s = getClass().getName();
        String message =this.code + ":" + getLocalizedMessage();
        return (message != null) ? (s + ": " + message) : s;
    }

}
