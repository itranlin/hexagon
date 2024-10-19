package com.itranlin.hexagon.core.impl;

import com.itranlin.hexagon.classloader.HexagonClass;
import com.itranlin.hexagon.classloader.PluginMeta;
import com.itranlin.hexagon.classloader.PluginMetaService;
import com.itranlin.hexagon.core.HexagonAppContext;
import com.itranlin.hexagon.core.ObjectStore;
import com.itranlin.hexagon.core.Plugin;
import com.itranlin.hexagon.core.PluginFilter;
import com.itranlin.hexagon.exception.HexagonUnknownException;
import com.itranlin.hexagon.spi.SpiFactory;
import net.dreamlu.mica.auto.annotation.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Hexagon app context.
 */
@AutoService(HexagonAppContext.class)
@SuppressWarnings("unused")
public class HexagonAppContextImpl implements HexagonAppContext {

    private final static Logger log = LoggerFactory.getLogger(HexagonAppContextImpl.class);

    private final List<String> all = new ArrayList<>();
    private final PluginFilter filter = SpiFactory.get(PluginFilter.class);
    private PluginMetaService metaService;
    private ObjectStore objectStore = new SimpleObjectStore();

    /**
     * Instantiates a new Hexagon app context.
     */
    public HexagonAppContextImpl() {
    }

    /**
     * Instantiates a new Hexagon app context.
     *
     * @param metaService meta service
     */
    public HexagonAppContextImpl(PluginMetaService metaService) {
        this.metaService = metaService;
    }

    /**
     * 设置 object store.
     *
     * @param objectStore object store
     */
    public void setObjectStore(ObjectStore objectStore) {
        this.objectStore = objectStore;
    }

    @Override
    public List<String> getAllPluginId() {
        return new ArrayList<>(all);
    }

    @Override
    public Plugin preInstall(File file) {
        return metaService.parse(file).conv();
    }

    @Override
    public Plugin install(File file) throws Throwable {
        PluginMeta fat = metaService.install(file);
        List<Class<?>> classes = fat.getScanner().scan();
        objectStore.registerCallback(classes, fat.getId());
        all.add(fat.getId());
        PluginLifeCycleHookManager.addHook(fat.getId());
        log.info("安装插件, 插件 ID = [{}], 配置=[{}]", fat.getId(), fat.getConfigSupportList());
        return fat.conv();
    }

    @Override
    public void uninstall(String pluginId) {
        objectStore.unregisterCallback(pluginId);
        metaService.unInstall(pluginId);
        all.remove(pluginId);
        PluginLifeCycleHookManager.removeHook(pluginId);
        log.info("卸载插件 {}", pluginId);
    }

    @Override
    public List<?> get(String tag) {
        List<HexagonClass> classes = metaService.get(tag);

        if (classes == null || classes.isEmpty()) {
            return new ArrayList<>();
        }

        if (filter == null) {
            return classes.stream().map(c -> objectStore.getObject(c.getClazz(), c.getPluginId())).collect(Collectors.toList());
        }

        return filter.filter(classes.stream().map(c -> new PluginFilter.FilterModel<>(objectStore.getObject(c.getClazz(), c.getPluginId()), c.getPluginId())).collect(Collectors.toList())).stream().map(PluginFilter.FilterModel::getBean).collect(Collectors.toList());
    }

    @Override
    public List<?> streamOne(Class<?> clazz) {
        return get(clazz.getName());
    }

    @Override
    public <T> Optional<T> get(String tag, String pluginId) {
        try {
            return Optional.ofNullable(metaService.get(tag, pluginId)).map(clazz -> objectStore.getObject(clazz.getClazz(), clazz.getPluginId()));
        } catch (Exception e) {
            throw new HexagonUnknownException(e);
        }
    }

    /**
     * 设置 plugin meta service.
     *
     * @param pluginMetaService plugin meta service
     */
    public void setPluginMetaService(PluginMetaService pluginMetaService) {
        this.metaService = pluginMetaService;
    }

    @Override
    public <R> R streamList(Class<?> clazz, Function<List<?>, R> ecs) {
        List<?> list = this.streamOne(clazz);
        if (list == null) {
            return null;
        }
        return ecs.apply(list);
    }

    @Override
    public <R, T> R stream(Class<T> clazz, String pluginId, Function<T, R> ec) {
        Optional<T> o = get(clazz.getName(), pluginId);
        return o.map(ec).orElse(null);
    }
}
