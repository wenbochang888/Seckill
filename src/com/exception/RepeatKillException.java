package com.exception;

/**
 * 重复秒杀的异常（运行期异常）
 * 
 * @author wenbochang
 * @date 2018年1月12日
 */

public class RepeatKillException extends SeckillException {

	private static final long serialVersionUID = 1L;

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepeatKillException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
}
