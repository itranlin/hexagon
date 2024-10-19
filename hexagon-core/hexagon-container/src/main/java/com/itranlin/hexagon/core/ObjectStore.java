package com.itranlin.hexagon.core;

import java.util.List;

/**
 * 对象存储仓库, 默认是存在 spring 容器里.
 * 可以实现为 map 之类的.
 **/
public interface ObjectStore {

    /**
     * 注册插件到对象仓库
     *
     * @param classes     插件里的核心 class, hexagonBean 标记的 class.
     * @param pluginId 插件 id;
     */
    void registerCallback(List<Class<?>> classes, String pluginId);

    /**
     * 从对象仓库将这个插件的东西全部删除.
     *
     * @param pluginId 插件ID
     */
    void unregisterCallback(String pluginId);

    /**
     * 从对象仓库获取某个对象
     *
     * @param <T> 对象类型
     * @param pluginId  插件ID
     * @param clazz 对象类型
     * @return 对象
     */
    <T> T getObject(Class<?> clazz, String pluginId);

    /**
     * 获取原始对象
     *
     * @return 原始对象
     */
    Object getOrigin();
}
