package org.xixi.exception;

public class ParamsException extends RuntimeException {

	public ParamsException() {
		super();
	}

	public ParamsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ParamsException(String arg0) {
		super(arg0);
	}

	public ParamsException(Throwable arg0) {
		super(arg0);
	}

}
