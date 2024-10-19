package com.itranlin.hexagon.classloader.support;

import com.itranlin.hexagon.constant.Constant;
import com.itranlin.hexagon.core.ConfigSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * ConfigSupportClassLoader
 */
public class ConfigSupportClassLoader extends URLClassLoader {


    private final static Logger log = LoggerFactory.getLogger(ConfigSupportClassLoader.class);

    /**
     * 实例话ConfigSupportClassLoader
     *
     * @param url 路径
     * @throws MalformedURLException exception
     */
    public ConfigSupportClassLoader(File url) throws MalformedURLException {
        super(new URL[]{url.toURI().toURL()}, Thread.currentThread().getContextClassLoader());
    }

    /**
     * 获取ConfigSupport列表
     *
     * @return ConfigSupport列表
     */
    public List<ConfigSupport> get() {
        try (InputStream resourceAsStream = getResourceAsStream(Constant.PLUGIN_META_FILE_NAME)) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            return Optional.ofNullable(properties.get(Constant.PLUGIN_BOOT_CLASS)).map(name -> {
                try {
                    return loadClass(name.toString());
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage(), e);
                    return null;
                }
            }).map(clazz -> Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.getType().equals(ConfigSupport.class)).map(field -> {
                try {
                    return (ConfigSupport) field.get(clazz);
                } catch (IllegalAccessException e) {
                    return null;
                }
            }).filter(Objects::nonNull).toList()).orElse(new ArrayList<>());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
