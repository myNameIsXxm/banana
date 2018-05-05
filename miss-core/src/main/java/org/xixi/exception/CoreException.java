package org.xixi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoreException extends RuntimeException {

	private static final long serialVersionUID = 1492909694069493905L;
	
	private static final Logger logger = LoggerFactory.getLogger(CoreException.class);

	public CoreException() {
		super();
	}

	public CoreException(String message) {
		super(message);
		logger.error(message);
	}

	public CoreException(Throwable cause) {
		super(cause);
		logger.error(cause.getMessage(), cause);
	}

	public CoreException(String message, Throwable cause) {
		super(message, cause);
		logger.error(message, cause);
	}

	public static CoreException error(String msg, java.lang.Throwable cause) {
		return new CoreException(msg, cause);
	}

	public static CoreException error(java.lang.Throwable cause) {
		return new CoreException(cause);
	}

	public static CoreException error(String msg) {
		return new CoreException(msg);
	}
}
