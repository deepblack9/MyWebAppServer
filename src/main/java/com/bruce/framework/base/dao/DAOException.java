package com.bruce.framework.base.dao;

import com.bruce.framework.exception.BaseCheckedException;

/**
 * @author aladding
 * Aug 19, 2009
 */
public class DAOException extends BaseCheckedException {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public DAOException() {

	}

	/**
	 * @param message
	 * @param cause
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}

}
