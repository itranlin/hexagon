package com.itranlin.hexagon.core;

import java.util.function.Supplier;

/**
 * The interface Plugin life cycle hook.
 */
public interface PluginLifeCycleHook {

    /**
     * Post init.
     *
     * @param objectStore 对象存储仓库
     * @param clazz       对象类型
     * @param bean        对象
     * @param beanName    对象名
     */
    void postInit(ObjectStore objectStore, Class<?> clazz, Supplier<Object> bean, String beanName);

    /**
     * Post destroy.
     *
     * @param objectStore 对象存储仓库
     * @param clazz       对象类型
     * @param beanName    对象名
     */
    void postDestroy(ObjectStore objectStore, Class<?> clazz, String beanName);

}
