package com.duduyixia.config.server.web;

public final class Response<T> {

    private final int code;
    private final String msg;
    private final T data;

    public Response(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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
