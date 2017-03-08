package com.ehu.bean.base;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-08 14:41.
 */
public class Response {
    private Integer code = 200;

    private String messages = "SUCCESS";

    private Object data;

    public Response() {
    }

    public Response(Object data) {
        this.data = data;
    }

    public Response(Integer code, String messages, Object data) {
        this.code = code;
        this.messages = messages;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
