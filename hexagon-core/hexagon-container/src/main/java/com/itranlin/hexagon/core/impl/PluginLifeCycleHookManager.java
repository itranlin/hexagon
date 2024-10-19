package com.itranlin.hexagon.core.impl;

import com.itranlin.hexagon.core.HexagonAppContext;
import com.itranlin.hexagon.core.ObjectStore;
import com.itranlin.hexagon.core.PluginLifeCycleHook;
import com.itranlin.hexagon.spi.HexagonAppContextSpiFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * The type Plugin life cycle hook manager.
 */
public class PluginLifeCycleHookManager {

    /**
     * The Cache.
     */
    static final Map<String, PluginLifeCycleHook> cache = new HashMap<>();
    private static final HexagonAppContext APP_CONTEXT = HexagonAppContextSpiFactory.getFirst();

    /**
     * Post init.
     *
     * @param objectStore 对象存储仓库
     * @param clazz       clazz
     * @param bean        对象
     * @param beanName    bean name
     */
    public static void postInit(ObjectStore objectStore, Class<?> clazz, Supplier<Object> bean, String beanName) {
        cache.values().forEach(c -> c.postInit(objectStore, clazz, bean, beanName));
    }


    /**
     * Post destroy.
     *
     * @param objectStore 对象存储仓库
     * @param clazz       class
     * @param beanName    bean name
     */
    public static void postDestroy(ObjectStore objectStore, Class<?> clazz, String beanName) {
        cache.values().forEach(c -> c.postDestroy(objectStore, clazz, beanName));
    }

    /**
     * 插件安装时添加钩子
     *
     * @param pluginId 插件ID
     */
    public static void addHook(String pluginId) {
        PluginLifeCycleHook hook = (PluginLifeCycleHook) APP_CONTEXT.get(PluginLifeCycleHook.class.getName(), pluginId).orElse(null);
        if (hook != null) {
            cache.put(pluginId, hook);
        }
    }

    /**
     * 插件卸载时删除钩子
     *
     * @param pluginId 插件ID
     */
    public static void removeHook(String pluginId) {
        cache.remove(pluginId);
    }
}
