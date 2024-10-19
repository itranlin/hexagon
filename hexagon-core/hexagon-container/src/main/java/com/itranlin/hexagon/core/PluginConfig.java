package com.itranlin.hexagon.core;

import com.itranlin.hexagon.spi.SpiFactory;

/**
 * 插件配置获取器的SPI.
 */
@HexagonSpi
public interface PluginConfig {

    /**
     * 通过SPI获取插件配置器.
     *
     * @return PluginConfig spi
     */
    static PluginConfig getSpi() {
        return SpiFactory.get(PluginConfig.class, new PluginConfigDefault());
    }

    /**
     * 获取配置.
     *
     * @param pluginId     插件ID
     * @param key          配置键
     * @param defaultValue 默认值
     * @return 指定键的值
     */
    String getProperty(String pluginId, String key, String defaultValue);

}
