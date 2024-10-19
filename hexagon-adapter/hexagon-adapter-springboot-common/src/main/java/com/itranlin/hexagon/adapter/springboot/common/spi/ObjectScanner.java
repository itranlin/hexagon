package com.itranlin.hexagon.adapter.springboot.common.spi;

import com.itranlin.hexagon.core.ObjectStore;

import java.util.List;

/**
 * 扫描器.
 */
public interface ObjectScanner {

    /**
     * 初始化扫描器.
     *
     * @param objectStore 对象存储仓库
     */
    void init(ObjectStore objectStore);

    /**
     * 注册Api.
     *
     * @param classes  扫描到的Class
     * @param pluginId 插件ID
     */
    void registerApis(List<Class<?>> classes, String pluginId);

    /**
     * 卸载Apis.
     *
     * @param pluginId 插件ID
     */
    void unregisterApis(String pluginId);
}
