package com.itranlin.hexagon.classloader;

import com.itranlin.hexagon.core.ConfigSupport;
import com.itranlin.hexagon.core.HexagonBoot;
import com.itranlin.hexagon.core.Plugin;
import com.itranlin.hexagon.core.scanner.PluginObjectScanner;
import com.itranlin.hexagon.utils.UniqueNameUtil;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * 插件元数据.
 */
@SuppressWarnings("unused")
public class PluginMeta {

    private String domain;

    private String desc;

    private String version;

    private String ext;

    private String bootClass;

    private List<ConfigSupport> configSupportList;

    private PluginObjectScanner scanner;
    private Map<String, String> extensionMappings;
    private Path location;
    private ClassLoader classLoader;
    private HexagonBoot hexagonBoot;

    /**
     * Builder
     *
     * @return plugin meta builder
     */
    public static PluginMetaBuilder builder() {
        return new PluginMetaBuilder();
    }

    /**
     * 插件ID.
     *
     * @return id
     */
    public String getId() {
        return this.domain + UniqueNameUtil.PLUGIN_ID_SPLIT + this.version;
    }

    /**
     * 插件domain.
     *
     * @return 插件domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置插件domain.
     *
     * @param domain 插件domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * 获取插件描述.
     *
     * @return 插件描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 设置插件描述.
     *
     * @param desc 插件描述
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 获取插件版本.
     *
     * @return 插件版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * 设置 插件版本.
     *
     * @param version 插件版本
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 获取 扩展数据.
     *
     * @return 扩展数据
     */
    public String getExt() {
        return ext;
    }

    /**
     * 设置 扩展数据.
     *
     * @param ext 扩展数据
     */
    public void setExt(String ext) {
        this.ext = ext;
    }

    /**
     * 获取 boot class.
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
     * 获取 config support list.
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
     * 转换为 plugin实例.
     *
     * @return plugin实例
     */
    public Plugin conv() {

        return Plugin.builder()
                .id(this.getId())
                .domain(this.domain)
                .configSupportList(this.configSupportList)
                .bootClass(this.bootClass)
                .desc(this.desc)
                .ext(this.ext)
                .version(this.version)
                .build();
    }

    /**
     * 获取 scanner.
     *
     * @return scanner
     */
    public PluginObjectScanner getScanner() {
        return scanner;
    }

    /**
     * 设置 scanner.
     *
     * @param scanner scanner
     */
    public void setScanner(PluginObjectScanner scanner) {
        this.scanner = scanner;
    }

    /**
     * 获取 extension mappings.
     *
     * @return extension mappings
     */
    public Map<String, String> getExtensionMappings() {
        return extensionMappings;
    }

    /**
     * 设置 extension mappings.
     *
     * @param extensionMappings extension mappings
     */
    public void setExtensionMappings(Map<String, String> extensionMappings) {
        this.extensionMappings = extensionMappings;
    }

    /**
     * 获取 location.
     *
     * @return location
     */
    public Path getLocation() {
        return location;
    }

    /**
     * 设置 location.
     *
     * @param location location
     */
    public void setLocation(Path location) {
        this.location = location;
    }

    /**
     * 获取 class loader.
     *
     * @return class loader
     */
    public ClassLoader getClassLoader() {
        return classLoader;
    }

    /**
     * 设置 class loader.
     *
     * @param classLoader class loader
     */
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * 获取 hexagon boot.
     *
     * @return hexagon boot
     */
    public HexagonBoot getHexagonBoot() {
        return hexagonBoot;
    }

    /**
     * 设置 hexagon boot.
     *
     * @param hexagonBoot hexagon boot
     */
    public void setHexagonBoot(HexagonBoot hexagonBoot) {
        this.hexagonBoot = hexagonBoot;
    }

    /**
     * Plugin meta builder.
     */
    public static final class PluginMetaBuilder {
        private PluginObjectScanner scanner;
        private Map<String, String> extensionMappings;
        private Path location;
        private ClassLoader classLoader;
        private HexagonBoot hexagonBoot;
        private String pluginDomain;
        private String pluginDesc;
        private String pluginVersion;
        private String pluginExt;
        private String pluginBootClass;
        private List<ConfigSupport> configSupportList;

        private PluginMetaBuilder() {
        }


        /**
         * 设置 scanner.
         *
         * @param scanner scanner
         * @return plugin meta builder
         */
        public PluginMetaBuilder scanner(PluginObjectScanner scanner) {
            this.scanner = scanner;
            return this;
        }

        /**
         * 设置 scanner Extension mappings.
         *
         * @param extensionMappings extension mappings
         * @return plugin meta builder
         */
        public PluginMetaBuilder extensionMappings(Map<String, String> extensionMappings) {
            this.extensionMappings = extensionMappings;
            return this;
        }

        /**
         * 设置 Location.
         *
         * @param location location
         * @return plugin meta builder
         */
        public PluginMetaBuilder location(Path location) {
            this.location = location;
            return this;
        }

        /**
         * 设置 Class loader.
         *
         * @param classLoader class loader
         * @return plugin meta builder
         */
        public PluginMetaBuilder classLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        /**
         * 设置 Hexagon boot.
         *
         * @param hexagonBoot hexagon boot
         * @return plugin meta builder
         */
        public PluginMetaBuilder hexagonBoot(HexagonBoot hexagonBoot) {
            this.hexagonBoot = hexagonBoot;
            return this;
        }

        /**
         * 设置 Plugin domain
         *
         * @param pluginDomain plugin domain
         * @return plugin meta builder
         */
        public PluginMetaBuilder pluginDomain(String pluginDomain) {
            this.pluginDomain = pluginDomain;
            return this;
        }

        /**
         * 设置 Plugin desc
         *
         * @param pluginDesc plugin desc
         * @return plugin meta builder
         */
        public PluginMetaBuilder pluginDesc(String pluginDesc) {
            this.pluginDesc = pluginDesc;
            return this;
        }

        /**
         * 设置 Plugin version
         *
         * @param pluginVersion plugin version
         * @return plugin meta builder
         */
        public PluginMetaBuilder pluginVersion(String pluginVersion) {
            this.pluginVersion = pluginVersion;
            return this;
        }

        /**
         * 设置 Plugin ext
         *
         * @param pluginExt plugin ext
         * @return plugin meta builder
         */
        public PluginMetaBuilder pluginExt(String pluginExt) {
            this.pluginExt = pluginExt;
            return this;
        }

        /**
         * 设置 Plugin boot class
         *
         * @param pluginBootClass plugin boot class
         * @return plugin meta builder
         */
        public PluginMetaBuilder pluginBootClass(String pluginBootClass) {
            this.pluginBootClass = pluginBootClass;
            return this;
        }

        /**
         * 设置 Config support list
         *
         * @param configSupportList config support list
         * @return plugin meta builder
         */
        public PluginMetaBuilder configSupportList(List<ConfigSupport> configSupportList) {
            this.configSupportList = configSupportList;
            return this;
        }

        /**
         * Build plugin meta.
         *
         * @return plugin meta
         */
        public PluginMeta build() {
            PluginMeta pluginMeta = new PluginMeta();
            pluginMeta.setScanner(scanner);
            pluginMeta.setExtensionMappings(extensionMappings);
            pluginMeta.setLocation(location);
            pluginMeta.setClassLoader(classLoader);
            pluginMeta.setHexagonBoot(hexagonBoot);
            pluginMeta.setDomain(pluginDomain);
            pluginMeta.setDesc(pluginDesc);
            pluginMeta.setVersion(pluginVersion);
            pluginMeta.setExt(pluginExt);
            pluginMeta.setBootClass(pluginBootClass);
            pluginMeta.setConfigSupportList(configSupportList);
            return pluginMeta;
        }
    }
}
