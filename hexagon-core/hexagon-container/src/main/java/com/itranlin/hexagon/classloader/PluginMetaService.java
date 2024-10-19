package com.itranlin.hexagon.classloader;

import com.itranlin.hexagon.spi.SpiFactory;

import java.io.File;
import java.util.List;

/**
 * PluginMetaService.
 */
public interface PluginMetaService {

    /**
     * 获取 PluginMetaService spi.
     *
     * @return spi
     */
    static PluginMetaService getSpi() {
        return SpiFactory.get(PluginMetaService.class);
    }

    /**
     * 获得 config.
     *
     * @param pluginMetaConfig 设置plugin meta config
     */
    void setConfig(PluginMetaConfig pluginMetaConfig);

    /**
     * Parse plugin meta.
     *
     * @param file 设置file
     * @return plugin meta
     */
    PluginMeta parse(File file);

    /**
     * Install plugin meta.
     *
     * @param file 设置file
     * @return plugin meta
     * @throws Throwable 设置throwable
     */
    PluginMeta install(File file) throws Throwable;

    /**
     * Un install.
     *
     * @param pluginId 设置plugin id
     */
    void unInstall(String pluginId);

    /**
     * Get list.
     *
     * @param extClass 设置ext class
     * @return list
     */
    List<HexagonClass> get(Class<?> extClass);

    /**
     * Get list.
     *
     * @param tag 设置tag
     * @return list
     */
    List<HexagonClass> get(String tag);

    /**
     * Get hexagon class.
     *
     * @param tag      设置tag
     * @param pluginId 设置plugin id
     * @return hexagon class
     * @throws ClassNotFoundException 设置class not found exception
     */
    HexagonClass get(String tag, String pluginId) throws ClassNotFoundException;
}
