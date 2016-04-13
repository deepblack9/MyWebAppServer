package com.bruce.framework.exception;

/**
 * 检测的异常基类
 * 
 * @author aladding
 * Aug 8, 2009
 */
public class BaseUncheckedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseUncheckedException() {
		super();
	}

	public BaseUncheckedException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseUncheckedException(String message) {
		super(message);
	}

	public BaseUncheckedException(Throwable cause) {
		super(cause);
	}

}
