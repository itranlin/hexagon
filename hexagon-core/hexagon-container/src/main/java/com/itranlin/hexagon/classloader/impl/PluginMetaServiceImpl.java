package com.itranlin.hexagon.classloader.impl;

import com.itranlin.hexagon.classloader.HexagonClass;
import com.itranlin.hexagon.classloader.PluginMeta;
import com.itranlin.hexagon.classloader.PluginMetaConfig;
import com.itranlin.hexagon.classloader.PluginMetaService;
import com.itranlin.hexagon.classloader.support.ClassLoaderFinder;
import com.itranlin.hexagon.classloader.support.MetaConfigReader;
import com.itranlin.hexagon.core.ConfigSupport;
import com.itranlin.hexagon.core.HexagonBoot;
import com.itranlin.hexagon.core.scanner.PluginObjectScanner;
import com.itranlin.hexagon.exception.HexagonInstallException;
import com.itranlin.hexagon.utils.HexagonFilesUtils;
import com.itranlin.hexagon.utils.StringUtil;
import net.dreamlu.mica.auto.annotation.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * PluginMetaServiceImpl.
 */
@SuppressWarnings("unused")
@AutoService(PluginMetaService.class)
public class PluginMetaServiceImpl implements PluginMetaService {

    private final static Logger log = LoggerFactory.getLogger(PluginMetaServiceImpl.class);

    private final Map<String/* pluginId */, PluginMeta> cache = new HashMap<>();
    private final Map<String/* tag */, Set<String/* pluginId */>> extCache = new HashMap<>();
    private final Set<String/* pluginId */> ids = new HashSet<>();
    private PluginMetaConfig pluginMetaConfig = new PluginMetaConfig();

    /**
     * 获取配置支持的字段.
     *
     * @param clazz    类
     * @param pluginId 插件ID
     * @return 定义过ConfigSupport的字段
     */
    public static List<ConfigSupport> getConfigSupportFields(Class<?> clazz, String pluginId) {
        return Stream.of(clazz.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()) && field.getType().isAssignableFrom(ConfigSupport.class) && field.canAccess(clazz))
                .map(field -> {
                    try {
                        return (ConfigSupport) field.get(clazz);
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                }).filter(Objects::nonNull)
                .peek(field -> field.setPluginId(pluginId))
                .toList();
    }

    @Override
    public void setConfig(PluginMetaConfig pluginMetaConfig) {
        this.pluginMetaConfig = pluginMetaConfig;
    }

    @Override
    public PluginMeta parse(File file) {
        return MetaConfigReader.getMeta(file);
    }


    @Override
    public PluginMeta install(File file) throws Throwable {
        PluginMeta meta = MetaConfigReader.getMeta(file);
        if (ids.contains(meta.getId())) {
            throw new HexagonInstallException("不要重复安装插件 " + meta.getId());
        }
        Map<String, String> mapping = MetaConfigReader.getExtension(file);

        String dir = pluginMetaConfig.getWorkDir() + File.separator + meta.getId();
        Path path = Paths.get(dir);
        if (Files.exists(path)) {
            if (pluginMetaConfig.getAutoDelete()) {
                log.warn("---->>>>> 插件目录已经存在, 删除 = {}", dir);
                HexagonFilesUtils.deleteDeep(path);
            } else {
                throw new HexagonInstallException("插件目录已经存在, 请先卸载再安装.");
            }
        }

        PluginClassLoader classLoader = ClassLoaderFinder.find(file, dir);
        meta.setClassLoader(classLoader);
        PluginObjectScanner scanner = new PluginObjectScanner();
        if (meta.getBootClass() != null) {
            Class<?> clazz = classLoader.loadClass(meta.getBootClass());
            if (HexagonBoot.class.isAssignableFrom(clazz)) {
                meta.setConfigSupportList(getConfigSupportFields(clazz, meta.getId()));
                HexagonBoot hexagonBoot = (HexagonBoot) clazz.getDeclaredConstructor().newInstance();
                scanner = hexagonBoot.getScanner();
                hexagonBoot.onStart(meta.getId());
                meta.setHexagonBoot(hexagonBoot);
            }
        } else {
            scanner.setPluginClassLoader(classLoader);
            scanner.setScanPath(meta.getDomain());
            scanner.setLocation(classLoader.getPath());
        }

        meta.setScanner(scanner);
        meta.setExtensionMappings(mapping);
        meta.setLocation(path);

        cache.put(meta.getId(), meta);

        mapping.forEach((key, value) -> extCache.computeIfAbsent(key, k -> new HashSet<>()).add(meta.getId()));

        ids.add(meta.getId());

        return meta;
    }

    @Override
    public void unInstall(String pluginId) {
        PluginMeta pluginMeta = cache.get(pluginId);
        if (pluginMeta == null) {
            log.warn("请检查你的 pluginId 是否正确 {} ", pluginId);
            return;
        }
        Path location = pluginMeta.getLocation();
        try {
            HexagonFilesUtils.deleteDeep(location);
        } catch (IOException e) {
            log.warn("删除插件目录失败：{}", location);
        }
        cache.remove(pluginId);
        for (Map.Entry<String, Set<String>> entry : extCache.entrySet()) {
            entry.getValue().removeIf(s -> s.equals(pluginId));
        }

        if (pluginMeta.getHexagonBoot() != null) {
            Optional.of(pluginMeta.getHexagonBoot()).ifPresent(HexagonBoot::onStop);
        }
        ids.remove(pluginId);
    }

    @Override
    public List<HexagonClass> get(Class<?> extClass) {
        return get(extClass.getName());
    }

    @Override
    public List<HexagonClass> get(String tag) {
        return Optional.ofNullable(extCache.get(tag)).orElseGet(HashSet::new).stream().map(pluginId -> {
            try {
                return get(tag, pluginId);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }).filter(Objects::nonNull).toList();

    }

    @Override
    public HexagonClass get(String tag, String pluginId) throws ClassNotFoundException {
        PluginMeta pluginMeta = cache.get(pluginId);
        if (pluginMeta == null) {
            return null;
        }
        String extImpl = pluginMeta.getExtensionMappings().get(tag);
        if (StringUtil.isEmpty(extImpl)) {
            return null;
        }
        Class<?> clazz = pluginMeta.getClassLoader().loadClass(extImpl);
        return new HexagonClass(clazz, pluginId);
    }

}
