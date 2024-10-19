package com.itranlin.hexagon.adapter.springboot.common.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.itranlin.hexagon.json.HexagonJson;
import net.dreamlu.mica.auto.annotation.AutoService;

import java.io.IOException;

/**
 * Json转换工具类
 *
 * @author itranlin
 * @since 2020 -01-09 18:28
 */
@AutoService(HexagonJson.class)
@SuppressWarnings("unused")
public class JacksonUtil implements HexagonJson {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    public <T> T fromString(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + json + " cannot be transformed to Json object");
        }
    }

    public <T> T fromString(String string, TypeReference<T> type) {
        try {
            return OBJECT_MAPPER.readValue(string, type);
        } catch (IOException e) {
            throw new IllegalArgumentException("The given string value: "
                    + string + " cannot be transformed to Json object");
        }
    }

    public String toString(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("The given Json object value: "
                    + value + " cannot be transformed to a String");
        }
    }

    public <T> T clone(T value, TypeReference<T> type) {
        return fromString(toString(value), type);
    }
}
