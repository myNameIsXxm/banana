package org.xixi.exception;

public class SqlException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SqlException() {
		super();
	}

	public SqlException(String msg) {
		super(msg);
	}

	public SqlException(String msg, Throwable e) {
		super(msg, e);
	}

	public SqlException(Throwable t) {
		super(t);
	}
}
