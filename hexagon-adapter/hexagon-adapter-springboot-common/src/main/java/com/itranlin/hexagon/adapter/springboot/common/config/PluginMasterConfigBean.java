package com.itranlin.hexagon.adapter.springboot.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 主程序的配置文件.
 */
@Configuration
@ConfigurationProperties(prefix = "com.itranlin.hexagon.master")
public class PluginMasterConfigBean {
    private String path = "plugins";

    private String workDir = "plugins_work";

    private Boolean autoDelete = true;
    private Boolean urlReplace = true;
    private String basePackage = "com.itranlin.hexagon";

    /**
     * 获取插件目录.
     *
     * @return 插件目录
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置插件目录.
     *
     * @param path 插件目录
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取插件工作目录.
     *
     * @return 插件工作目录
     */
    public String getWorkDir() {
        return workDir;
    }

    /**
     * 设置插件工作目录.
     *
     * @param workDir 插件工作目录
     */
    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    /**
     * 是否自动删除.
     *
     * @return 是否自动删除
     */
    public Boolean getAutoDelete() {
        return autoDelete;
    }

    /**
     * 设置删除模式.
     *
     * @param autoDelete 删除模式
     */
    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }

    /**
     * 获取Url替换模式.
     *
     * @return Url替换模式
     */
    public Boolean getUrlReplace() {
        return urlReplace;
    }

    /**
     * 设置Url替换模式.
     *
     * @param urlReplace Url替换模式
     */
    public void setUrlReplace(Boolean urlReplace) {
        this.urlReplace = urlReplace;
    }

    /**
     * 获取扩展点文档扫描基础包.
     *
     * @return 扩展点文档扫描基础包
     */
    public String getBasePackage() {
        return basePackage;
    }

    /**
     * 设置扩展点文档扫描基础包.
     *
     * @param basePackage 扩展点文档扫描基础包
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "PluginMasterConfigBean{" +
                "path='" + path + '\'' +
                ", workDir='" + workDir + '\'' +
                ", autoDelete=" + autoDelete +
                ", urlReplace=" + urlReplace +
                ", basePackage='" + basePackage + '\'' +
                '}';
    }
}
