package com.mocredit.integral.entity;

/**
 * 响应对象
 * 
 * @author liaoying Created on 2015年8月17日
 * 
 */
public class Response {
	private String success;
	private String errorMsg;
	private String errorCode;
	private Object data;

	public Response(String success, String errorMsg, String errorCode,
			Object data) {
		super();
		this.success = success;
		this.errorMsg = errorMsg;
		this.errorCode = errorCode;
		this.data = data;
	}

	public Response() {
		super();
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
