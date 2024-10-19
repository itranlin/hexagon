package com.itranlin.hexagon.core;

import com.itranlin.hexagon.core.scanner.PluginObjectScanner;

/**
 * 启动器的接口。
 */
public interface HexagonBoot {


    /**
     * 获得扫描器.
     *
     * @return 扫描器
     */
    PluginObjectScanner getScanner();

    /**
     * 主框架启动插件完成后的回调.
     *
     * @param pluginId 回调生成的插件ID
     */
    @SuppressWarnings("unused")
    default void onStart(String pluginId) {
    }

    /**
     * 主框架停止完成后的回调.
     */
    default void onStop() {
    }
}
