package com.duduyixia.config.common.http;

import java.io.Serializable;

public final class Response<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public Response() {}

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(0, "success", data);
    }

    public static <T> Response<T> fail(int code, String msg) {
        return new Response<>(0, msg, null);
    }

    public static <T> Response<T> fail() {
        return new Response<>(-1, "error", null);
    }

    public static <T> Response<T> fail(String msg) {
        return new Response<>(-1, msg, null);
    }
}
