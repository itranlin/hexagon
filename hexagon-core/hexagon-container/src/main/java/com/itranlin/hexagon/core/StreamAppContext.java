package com.itranlin.hexagon.core;

import java.util.List;
import java.util.function.Function;

/**
 * 流式 API, 优雅处理.
 **/
@SuppressWarnings("unused")
public interface StreamAppContext {

    /**
     * 简化操作, code 就是全路径类名
     *
     * @param clazz class
     * @return 对象列表
     */
    List<?> streamOne(Class<?> clazz);

    /**
     * 针对有返回值的 api, 需要支持流式调用
     *
     * @param <R> 目标类型
     * @param clazz class
     * @param ecs 处理
     * @return 对象
     */
    <R> R streamList(Class<?> clazz, Function<List<?>, R> ecs);

    /**
     * 针对有返回值的 api, 需要支持流式调用
     *
     * @param <R> 目标类型
     * @param <T> 原始类型
     * @param clazz class
     * @param pluginId 插件ID
     * @param ec 处理
     * @return 对象
     */
    <R, T> R stream(Class<T> clazz, String pluginId, Function<T, R> ec);
}
