package com.itranlin.hexagon.adapter.springboot3.starter.client;

import com.itranlin.hexagon.core.PluginConfig;
import net.dreamlu.mica.auto.annotation.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * 插件配置获取器的实现.
 */
@AutoService(PluginConfig.class)
@Component
public class PluginConfigImpl implements PluginConfig, EnvironmentAware {

    private final static Logger log = LoggerFactory.getLogger(PluginConfigImpl.class);

    /**
     * 环境变量.
     */
    static Environment environment;

    @Override
    public String getProperty(String pluginId, String key, String defaultValue) {
        log.info("plugin id {}", pluginId);
        if (environment == null) {
            log.warn("environment is null");
            return null;
        }
        return environment.getProperty(key, defaultValue);
    }

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        PluginConfigImpl.environment = environment;
    }
}
