package org.xixi.exception;

/**
 * 参考 javax.servlet.http.HttpServletResponse
 */
public enum HttpExceptionEnum {
	NOT_FOUND_EXCEPTION(404, "页面404", "/login/login"), 
	NOT_SUPPORTED_METHOD_EXCEPTION(405,"NOT_SUPPORTED_METHOD_EXCEPTION", "/error"), 
	NOT_SUPPORTED_MEDIA_TYPE_EXCEPTION(415,"NOT_SUPPORTED_MEDIA_TYPE_EXCEPTION", "/error"), 
	NOT_ACCEPTABLE_MEDIA_TYPE_EXCEPTION(405,"NOT_ACCEPTABLE_MEDIA_TYPE_EXCEPTION", "/error"), 
	MISSING_REQUEST_PARAMETER_EXCEPTION(501,"MISSING_REQUEST_PARAMETER_EXCEPTION", "/error"), 
	REQUEST_BINDING_EXCEPTION(501,"REQUEST_BINDING_EXCEPTION", "/error"), 
	NOT_SUPPORTED_CONVERSION_EXCEPTION(1, "NOT_SUPPORTED_CONVERSION_EXCEPTION","/error"), 
	TYPE_MISMATCH_EXCEPTION(2, "TYPE_MISMATCH_EXCEPTION","/error"), 
	MESSAGE_NOT_READABLE_EXCEPTION(3,"MESSAGE_NOT_READABLE_EXCEPTION","/error"), 
	MESSAGE_NOT_WRITABLE_EXCEPTION(4,"MESSAGE_NOT_WRITABLE_EXCEPTION","/error"), 
	NOT_VALID_METHOD_ARGUMENT_EXCEPTION(5,"NOT_VALID_METHOD_ARGUMENT_EXCEPTION","/error"), 
	MISSING_REQUEST_PART_EXCEPTION(6, "MISSING_REQUEST_PART_EXCEPTION", "/error"), 
	BIND_EXCEPTION(7, "BIND_EXCEPTION", "/error"), 
	ASYNC_REQUEST_TIMEOUT_EXCEPTION(8, "ASYNC_REQUEST_TIMEOUT_EXCEPTION", "/error"), 
	AUTH_EXCEPTION(9, "登录信息无效，请登登录后重试", "/login/login"), 
	CAN_NOT_CREATE_TRANSACTION_EXCEPTION(10, "数据库连接异常，请稍后重试", "/error"),
	NULL_POINTER_EXCEPTION(11,"系统空指针异常","/error"),
	HIBER_TRAN_EXCEPTION(12,"数据库连接异常，请稍后重试","/error");

	private int code;
	private String message;
	private String url;

	private HttpExceptionEnum(int code, String message, String url) {
		this.code = code;
		this.message = message;
		this.url = url;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
