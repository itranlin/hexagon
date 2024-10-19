package com.itranlin.hexagon.classloader;

/**
 * 封装插件的Class.
 */
public class HexagonClass {

    /**
     * 原始Class.
     */
    Class<?> clazz;

    /**
     * 插件ID.
     */
    String pluginId;

    /**
     * 构造方法.
     *
     * @param clazz    原始Class
     * @param pluginId 插件ID
     */
    public HexagonClass(Class<?> clazz, String pluginId) {
        this.clazz = clazz;
        this.pluginId = pluginId;
    }

    /**
     * 获取插件ID.
     *
     * @return 插件ID
     */
    public String getPluginId() {
        return pluginId;
    }

    /**
     * 获取原始Class.
     *
     * @return 原始Class
     */
    public Class<?> getClazz() {
        return clazz;
    }

}
