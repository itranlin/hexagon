package com.itranlin.hexagon.core;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Hexagon app context.
 */
@SuppressWarnings("unused")
public interface HexagonAppContext extends StreamAppContext {

    /**
     * 获取当前所有的插件 id
     *
     * @return 插件ID集合 all plugin id
     */
    List<String> getAllPluginId();


    /**
     * 预加载, 只读取元信息和 load boot class 和配置, 不做 bean 加载.
     *
     * @param file 插件文件
     * @return 插件信息 plugin
     */
    Plugin preInstall(File file);

    /**
     * 安装插件
     *
     * @param file 插件文件
     * @return 插件信息 plugin
     * @throws Throwable the throwable
     * @throws Throwable 安装异常
     */
    Plugin install(File file) throws Throwable;

    /**
     * 卸载插件
     *
     * @param pluginId 插件id
     */
    void uninstall(String pluginId);

    /**
     * 获取多个扩展点的插件信息
     *
     * @param tag 插件标记
     * @return 插件信息
     */
    List<?> get(String tag);

    /**
     * 获取单个插件实例.
     *
     * @param <T>      实例类型
     * @param tag      插件标记
     * @param pluginId 插件ID
     * @return 插件信息的Optional
     */
    <T> Optional<T> get(String tag, String pluginId);

}
