package com.itranlin.hexagon.classloader.support;

import com.itranlin.hexagon.classloader.PluginMeta;
import com.itranlin.hexagon.constant.Constant;
import com.itranlin.hexagon.core.ConfigSupport;
import com.itranlin.hexagon.exception.HexagonMatePropertyNotFoundException;
import com.itranlin.hexagon.exception.HexagonPropertyLoadException;
import com.itranlin.hexagon.exception.HexagonUnknownException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * MetaConfigReader
 */
public class MetaConfigReader {

    /**
     * 获取插件元信息
     *
     * @param file 插件文件
     * @return 元信息
     */
    public static PluginMeta getMeta(File file) {
        try (ConfigSupportClassLoader configSupportClassLoader = new ConfigSupportClassLoader(file)) {
            Properties properties = loadProperties(file.getAbsolutePath(), Constant.PLUGIN_META_FILE_NAME, false);
            String domain = properties.getProperty(Constant.PLUGIN_DOMAIN_KEY);
            String desc = properties.getProperty(Constant.PLUGIN_DESC_KEY);
            String version = properties.getProperty(Constant.PLUGIN_VERSION_KEY);
            String ext = properties.getProperty(Constant.PLUGIN_EXT_KEY);
            String boot = properties.getProperty(Constant.PLUGIN_BOOT_CLASS);
            List<ConfigSupport> list = configSupportClassLoader.get();
            return PluginMeta.builder().pluginDomain(domain).pluginDesc(desc).pluginVersion(version).pluginExt(ext).pluginBootClass(boot).configSupportList(list).build();
        } catch (Exception e) {
            throw new HexagonUnknownException(e);
        }
    }

    /**
     * 获取插件Extension定义
     *
     * @param file 插件文件
     * @return Extension定义
     */
    public static Map<String, String> getExtension(File file) {
        Properties properties = loadProperties(file.getAbsolutePath(), Constant.EXTENSION_FILE_NAME, true);
        Map<String, String> map = new HashMap<>();

        for (Map.Entry<Object, Object> i : properties.entrySet()) {
            map.put(i.getKey().toString(), i.getValue().toString());
        }

        return map;
    }


    private static Properties loadProperties(String file, String propertiesFileName, boolean ignoreNotFound) {
        String extension = FilenameUtils.getExtension(file);
        try {
            return switch (extension) {
                case "jar" -> loadPropertiesFormJar(file, propertiesFileName);
                case "zip" -> loadPropertiesFormZip(file, propertiesFileName);
                default -> new Properties();
            };
        } catch (HexagonMatePropertyNotFoundException e) {
            if (ignoreNotFound) {
                return new Properties();
            } else {
                throw e;
            }
        }
    }

    private static Properties loadPropertiesFormJar(String file, String propertiesFileName) {
        Properties properties = new Properties();
        try (JarFile jarFile = new JarFile(file)) {
            JarEntry entry = jarFile.getJarEntry(propertiesFileName);
            if (entry == null) {
                throw new HexagonMatePropertyNotFoundException();
            }
            try (InputStream inputStream = jarFile.getInputStream(entry)) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new HexagonPropertyLoadException(e);
        }

        return properties;
    }

    private static Properties loadPropertiesFormZip(String file, String propertiesFileName) {
        Properties properties = new Properties();
        try (ZipFile zipFile = ZipFile.builder().setFile(file).get()) {
            ZipArchiveEntry entry = zipFile.getEntry(propertiesFileName);
            if (entry == null) {
                throw new HexagonMatePropertyNotFoundException();
            }
            try (InputStream inputStream = zipFile.getInputStream(entry)) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            throw new HexagonPropertyLoadException(e);
        }
        return properties;
    }
}
