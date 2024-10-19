package com.itranlin.hexagon.utils;

/**
 * The type Unique name util.
 */
public class UniqueNameUtil {


    /**
     * 插件ID分割符.
     */
    public static final String PLUGIN_ID_SPLIT = "_";
    /**
     * 插件名分割符.
     */
    public static final String NAME_SPLIT = "___";

    /**
     * 生成一个 插件名.
     *
     * @param clazz    插件类型
     * @param pluginId 插件ID
     * @return 插件名
     */
    public static String getName(Class<?> clazz, String pluginId) {
        return clazz.getName() + NAME_SPLIT + pluginId;
    }

    /**
     * 获取插件ID
     *
     * @param name 插件名
     * @return the 插件ID
     */
    public static String getPluginId(String name) {
        if (!name.contains(NAME_SPLIT)) {
            return null;
        }
        return name.split(NAME_SPLIT)[1];
    }

}
