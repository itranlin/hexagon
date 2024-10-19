package com.itranlin.hexagon.core;

import java.util.List;

/**
 * Plugin filter.
 */
@HexagonSpi
@SuppressWarnings("unused")
public interface PluginFilter {

    /**
     * Filter list.
     *
     * @param <T>  范型参数
     * @param list 过滤器 list
     * @return the list
     */
    <T> List<FilterModel<T>> filter(List<FilterModel<T>> list);

    /**
     * FilterModel.
     *
     * @param <T> 范型参数
     */
    class FilterModel<T> {
        /**
         * T.
         */
        T bean;
        /**
         * Plugin id.
         */
        String pluginId;

        /**
         * Instantiates a new F model.
         *
         * @param bean     bean对象
         * @param pluginId 插件ID
         */
        public FilterModel(T bean, String pluginId) {
            this.bean = bean;
            this.pluginId = pluginId;
        }

        /**
         * 获得 bean对象.
         *
         * @return bean对象
         */
        public T getBean() {
            return bean;
        }

        /**
         * 设置 bean对象.
         *
         * @param bean bean对象
         */
        public void setBean(T bean) {
            this.bean = bean;
        }

        /**
         * 获得 插件ID.
         *
         * @return 插件ID
         */
        public String getPluginId() {
            return pluginId;
        }

        /**
         * 设置 插件ID.
         *
         * @param pluginId 插件ID
         */
        public void setPluginId(String pluginId) {
            this.pluginId = pluginId;
        }
    }
}
