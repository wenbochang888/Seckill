package com.exception;

/**
 * 
 * 秒杀关闭异常，秒杀关闭了，但仍有人继续post数据
 * 
 * @author wenbochang
 * @date 2018年1月12日
 */

public class SeckillCloseException extends SeckillException {

	private static final long serialVersionUID = 1L;

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SeckillCloseException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
