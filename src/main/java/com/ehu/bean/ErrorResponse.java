package com.ehu.bean;

import org.springframework.http.HttpStatus;

/**
 * 错误返回信息bean.
 *
 * @author demon
 * @since 2017-03-02 16:32.
 */
public class ErrorResponse {
    private HttpStatus code;

    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
