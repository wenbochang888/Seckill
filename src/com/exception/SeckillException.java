package com.exception;

/**
 * 
 * 所有其他未考虑的异常，都属于这个异常
 * 一定要继承RunntimeException这个类
 * 因为只有运行时异常 spring的事务才会进行回滚
 * 
 * @author wenbochang
 * @date 2018年1月12日
 */

public class SeckillException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SeckillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
