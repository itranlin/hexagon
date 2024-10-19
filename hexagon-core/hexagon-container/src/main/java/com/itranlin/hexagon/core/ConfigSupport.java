package com.itranlin.hexagon.core;

/**
 * Config support.
 */
@SuppressWarnings("unused")
public final class ConfigSupport {

    private String keyName;

    private String defaultValue;

    private String desc;

    private boolean required;

    private String pluginId;

    /**
     * Instantiates a new Config support.
     *
     * @param keyName key name
     */
    public ConfigSupport(String keyName) {
        this.keyName = keyName;
    }

    /**
     * Instantiates a new Config support.
     *
     * @param keyName      key
     * @param defaultValue 默认值
     */
    public ConfigSupport(String keyName, String defaultValue) {
        this.keyName = keyName;
        this.defaultValue = defaultValue;
    }

    /**
     * 获得 property.
     *
     * @return property
     */
    public String getProperty() {
        return PluginConfig.getSpi().getProperty(pluginId, keyName, defaultValue);
    }

    /**
     * 获得 key name.
     *
     * @return key name
     */
    public String getKeyName() {
        return keyName;
    }

    /**
     * 设置 key name.
     *
     * @param keyName key name
     */
    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    /**
     * 获得 default value.
     *
     * @return default value
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置 default value.
     *
     * @param defaultValue default value
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 获得 desc.
     *
     * @return desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置 desc.
     *
     * @param desc desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * Is required boolean.
     *
     * @return boolean
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * 设置 required.
     *
     * @param required required
     */
    public void setRequired(boolean required) {
        this.required = required;
    }

    /**
     * 获得 plugin id.
     *
     * @return plugin id
     */
    public String getPluginId() {
        return pluginId;
    }

    /**
     * 设置 plugin id.
     *
     * @param pluginId plugin id
     */
    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }
}
