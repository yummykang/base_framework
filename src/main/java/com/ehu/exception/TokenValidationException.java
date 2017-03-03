package com.ehu.exception;

/**
 * 自定义Token验证异常
 *
 * @author demon
 * @since
 */
public class TokenValidationException extends Exception {
	public TokenValidationException(String message) {
		super(message);
	}
}
