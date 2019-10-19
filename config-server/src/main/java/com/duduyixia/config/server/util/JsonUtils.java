package com.duduyixia.config.server.util;

import com.google.gson.Gson;

/**
 * created by WangTao on 2019-10-18
 */
public final class JsonUtils {

    private static final Gson GSON = new Gson();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T formJson(String json, Class<T> klass) {
        return GSON.fromJson(json, klass);
    }
}
