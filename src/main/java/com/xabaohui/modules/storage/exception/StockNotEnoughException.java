package com.xabaohui.modules.storage.exception;

/**
 * 库存量不足异常
 * 
 * @author yz
 * 
 */
public class StockNotEnoughException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StockNotEnoughException() {
		super();
	}

	public StockNotEnoughException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockNotEnoughException(String message) {
		super(message);
	}

	public StockNotEnoughException(Throwable cause) {
		super(cause);
	}

}
