package com.itranlin.hexagon.classloader;

/**
 * PluginMetaConfig.
 */
@SuppressWarnings("unused")
public class PluginMetaConfig {

    private String workDir;
    private Boolean autoDelete;


    /**
     * 默认构造函数.
     */
    public PluginMetaConfig() {
    }

    /**
     * 全参构造.
     *
     * @param workDir 获得work dir
     * @param autoDelete 自动删除已安装的目录
     */
    public PluginMetaConfig(String workDir, Boolean autoDelete) {
        this.workDir = workDir;
        this.autoDelete = autoDelete;
    }

    /**
     * 获得 work dir.
     *
     * @return 获得work dir
     */
    public String getWorkDir() {
        return workDir;
    }

    /**
     * 设置 work dir.
     *
     * @param workDir 获得work dir
     */
    public void setWorkDir(String workDir) {
        this.workDir = workDir;
    }

    /**
     * 获得 auto delete.
     *
     * @return 获得auto delete
     */
    public Boolean getAutoDelete() {
        return autoDelete;
    }

    /**
     * 设置 auto delete.
     *
     * @param autoDelete 获得auto delete
     */
    public void setAutoDelete(Boolean autoDelete) {
        this.autoDelete = autoDelete;
    }
}
