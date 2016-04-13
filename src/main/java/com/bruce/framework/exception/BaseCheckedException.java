package com.bruce.framework.exception;

/**
 * 检测的异常基类
 * 
 * @author aladding
 * Aug 8, 2009
 */
public class BaseCheckedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseCheckedException() {
		super();
	}

	public BaseCheckedException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseCheckedException(String message) {
		super(message);
	}

	public BaseCheckedException(Throwable cause) {
		super(cause);
	}

}
