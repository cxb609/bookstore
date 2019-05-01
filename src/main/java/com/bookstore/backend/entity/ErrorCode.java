package com.bookstore.backend.entity;

public enum ErrorCode {
    /**
     * 暂时只有一个错误代码400，后续实践中补上
     * 客户端错误
     */
    PARAM_ERR_COMMON(400_000, "Bad Request"),

    /**
     * 服务器端错误
     */
    SERVER_EXCEPTION(500_000, "服务器发生异常");

    private int statusCode;
    private String msg;

    ErrorCode(int statusCode,String msg){
        this.statusCode = statusCode;
        this.msg = msg;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMsg() {
        return msg;
    }
}
