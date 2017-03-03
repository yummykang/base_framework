package com.ehu.exception;

/**
 * 业务异常错误
 *
 * @author demon
 */
public class BusinessErrorException extends Exception {
    /**
     * 异常code
     */
    private String code;

    /**
     * 异常信息
     */
    private String message;

    public BusinessErrorException(String code) {
        this.code = code;
    }

    public BusinessErrorException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
