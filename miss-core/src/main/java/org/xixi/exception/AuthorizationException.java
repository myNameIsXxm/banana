package org.xixi.exception;

/**
 * 权限控制相关抛出的异常
 */
public class AuthorizationException extends Exception{

	public AuthorizationException(){
		super();
	}

	public AuthorizationException(String message){
		super(message);
	}

}
