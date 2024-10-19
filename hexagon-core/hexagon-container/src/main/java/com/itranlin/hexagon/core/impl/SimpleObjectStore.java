package com.itranlin.hexagon.core.impl;

import com.itranlin.hexagon.core.ObjectStore;
import com.itranlin.hexagon.exception.HexagonCallbackException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple object store.
 */
@SuppressWarnings("unchecked")
public class SimpleObjectStore implements ObjectStore {

    /**
     * The Plugin id mapping.
     */
    final Map<String/*pluginId*/, Map<String/*beanName*/, Object/*beanObject*/>> pluginIdMapping = new HashMap<>();

    /**
     * 注册插件到对象仓库
     *
     * @param classes     插件里的核心 class, hexagonBean 标记的 class.
     * @param pluginId 插件 id;
     */
    @Override
    public void registerCallback(List<Class<?>> classes, String pluginId) {
        Map<String, Object> store = new HashMap<>();
        classes.forEach(clazz -> {
            try {
                store.put(clazz.getName(), clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new HexagonCallbackException(e);
            }
        });
        pluginIdMapping.put(pluginId, store);
    }


    /**
     * 从对象仓库将这个插件的东西全部删除.
     *
     * @param pluginId 插件ID
     */
    @Override
    public void unregisterCallback(String pluginId) {
        pluginIdMapping.remove(pluginId);
    }

    /**
     * 从对象仓库获取某个对象
     *
     * @param <T> 对象类型
     * @param pluginId  插件ID
     * @param clazz 对象类型
     * @return 对象
     */
    @Override
    public <T> T getObject(Class<?> clazz, String pluginId) {
        Map<String, Object> map = pluginIdMapping.get(pluginId);
        if (map == null) {
            return null;
        }
        return (T) map.get(clazz.getName());
    }

    /**
     * 获取原始对象
     *
     * @return 原始对象
     */
    @Override
    public Object getOrigin() {
        return pluginIdMapping;
    }
}
