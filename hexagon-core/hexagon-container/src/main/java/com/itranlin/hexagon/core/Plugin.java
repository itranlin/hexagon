package com.itranlin.hexagon.core;

import java.util.List;
import java.util.Objects;

/**
 * Plugin.
 */
@SuppressWarnings("unused")
public class Plugin {

    /**
     * Id.
     */
    String id;

    /**
     * Domain.
     */
    String domain;

    /**
     * Desc.
     */
    String desc;

    /**
     * Version.
     */
    String version;

    /**
     * Ext.
     */
    String ext;

    /**
     * Boot class.
     */
    String bootClass;

    private List<ConfigSupport> configSupportList;

    /**
     * Builder plugin builder.
     *
     * @return plugin builder
     */
    public static PluginBuilder builder() {
        return new PluginBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Plugin plugin) {
            return Objects.equals(id, plugin.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    /**
     * 获得 id.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置 id.
     *
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获得 domain.
     *
     * @return domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置 domain.
     *
     * @param domain domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
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
     * 获得 version.
     *
     * @return version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置 version.
     *
     * @param version version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获得 ext.
     *
     * @return ext
     */
    public String getExt() {
        return ext;
    }

    /**
     * 设置 ext.
     *
     * @param ext ext
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * 获得 boot class.
     *
     * @return boot class
     */
    public String getBootClass() {
        return bootClass;
    }

    /**
     * 设置 boot class.
     *
     * @param bootClass boot class
     */
    public void setBootClass(String bootClass) {
        this.bootClass = bootClass;
    }

    /**
     * 获得 config support list.
     *
     * @return config support list
     */
    public List<ConfigSupport> getConfigSupportList() {
        return configSupportList;
    }

    /**
     * 设置 config support list.
     *
     * @param configSupportList config support list
     */
    public void setConfigSupportList(List<ConfigSupport> configSupportList) {
        this.configSupportList = configSupportList;
    }

    /**
     * type Plugin builder.
     */
    public static final class PluginBuilder {
        private String id;
        private String domain;
        private String desc;
        private String version;
        private String ext;
        private String bootClass;
        private List<ConfigSupport> configSupportList;

        private PluginBuilder() {
        }

        /**
         * Id plugin builder.
         *
         * @param id id
         * @return plugin builder
         */
        public PluginBuilder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Domain plugin builder.
         *
         * @param domain domain
         * @return plugin builder
         */
        public PluginBuilder domain(String domain) {
            this.domain = domain;
            return this;
        }

        /**
         * Desc plugin builder.
         *
         * @param desc desc
         * @return plugin builder
         */
        public PluginBuilder desc(String desc) {
            this.desc = desc;
            return this;
        }

        /**
         * Version plugin builder.
         *
         * @param version version
         * @return plugin builder
         */
        public PluginBuilder version(String version) {
            this.version = version;
            return this;
        }

        /**
         * Ext plugin builder.
         *
         * @param ext ext
         * @return plugin builder
         */
        public PluginBuilder ext(String ext) {
            this.ext = ext;
            return this;
        }

        /**
         * Boot class plugin builder.
         *
         * @param bootClass boot class
         * @return plugin builder
         */
        public PluginBuilder bootClass(String bootClass) {
            this.bootClass = bootClass;
            return this;
        }

        /**
         * Config support list plugin builder.
         *
         * @param configSupportList config support list
         * @return plugin builder
         */
        public PluginBuilder configSupportList(List<ConfigSupport> configSupportList) {
            this.configSupportList = configSupportList;
            return this;
        }

        /**
         * Build plugin.
         *
         * @return plugin
         */
        public Plugin build() {
            Plugin plugin = new Plugin();
            plugin.setId(id);
            plugin.setDomain(domain);
            plugin.setDesc(desc);
            plugin.setVersion(version);
            plugin.setExt(ext);
            plugin.setBootClass(bootClass);
            plugin.setConfigSupportList(configSupportList);
            return plugin;
        }
    }
}
