package com.itranlin.hexagon.json;


import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Hexagon的json处理.
 */
@SuppressWarnings("unused")
public interface HexagonJson {

    /**
     * 反序列化JSON到对象.
     *
     * @param <T>   对象类型
     * @param json  json字符串
     * @param clazz 对象类型
     * @return 对象
     */
    <T> T fromString(String json, Class<T> clazz);


    /**
     * 反序列化JSON到对象（针对范型类型）.
     *
     * @param <T>   对象类型
     * @param json  json字符串
     * @param clazz 类型Type
     * @return the t
     */
    <T> T fromString(String json, TypeReference<T> clazz);

    /**
     * 对象序列化成字符串.
     *
     * @param value 对象实例
     * @return 序列化字符串
     */
    String toString(Object value);

    /**
     * 利用JSON序列化克隆对象.
     *
     * @param <T>   对象类型
     * @param value 需要克隆的对象
     * @param type  类型Type
     * @return 克隆后的新对象
     */
    <T> T clone(T value, TypeReference<T> type);
}
