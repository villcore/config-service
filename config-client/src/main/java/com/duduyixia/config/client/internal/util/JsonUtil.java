package com.duduyixia.config.client.internal.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * created by WangTao on 2019-10-29
 */
public final class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    private JsonUtil() {}

    public static String toJson(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) throws IOException {
        return OBJECT_MAPPER.readValue(json, typeReference);
    }

    public static <T> T fromJson(String json, Class<T> klass) throws IOException {
        return OBJECT_MAPPER.readValue(json, klass);
    }
}
