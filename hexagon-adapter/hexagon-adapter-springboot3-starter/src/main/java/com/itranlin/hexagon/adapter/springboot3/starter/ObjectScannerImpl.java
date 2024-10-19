package com.itranlin.hexagon.adapter.springboot3.starter;

import com.itranlin.hexagon.adapter.springboot.common.RestUrlScanFactory;
import com.itranlin.hexagon.adapter.springboot.common.RestUrlScanner;
import com.itranlin.hexagon.adapter.springboot.common.spi.ObjectScanner;
import com.itranlin.hexagon.core.ObjectStore;
import com.itranlin.hexagon.utils.UniqueNameUtil;
import net.dreamlu.mica.auto.annotation.AutoService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * SpringBoot3 的扫描器实现.
 */
@AutoService(ObjectScanner.class)
@SuppressWarnings("unused")
public class ObjectScannerImpl implements ObjectScanner {

    private final Map<String, List<Class<?>>> cache = new HashMap<>();

    private final Map<String, RestUrlScanner> restUrlScanCache = new HashMap<>();

    private BeanFactory beanFactory;
    private RequestMappingHandlerMapping mapping;
    private RequestMappingHandlerAdapter adapter;

    private volatile boolean init;

    @Override
    public void init(ObjectStore objectStore) {
        if (!init) {
            synchronized (this) {
                if (!init) {
                    beanFactory = (BeanFactory) objectStore.getOrigin();
                    mapping = (RequestMappingHandlerMapping) beanFactory.getBean("requestMappingHandlerMapping");
                    adapter = (RequestMappingHandlerAdapter) beanFactory.getBean("requestMappingHandlerAdapter");
                    init = true;
                }
            }
        }
    }

    @Override
    public void registerApis(List<Class<?>> classes, String pluginId) {

        classes.forEach(clazz -> {
            try {
                String name = UniqueNameUtil.getName(clazz, pluginId);
                RestUrlScanner restUrlScanner = RestUrlScanFactory.getInstance().create(beanFactory.getBean(name), mapping, adapter, pluginId, () -> "true");
                restUrlScanner.register();
                restUrlScanCache.put(name, restUrlScanner);
            } catch (Exception ignore) {
            }
        });
        cache.put(pluginId, classes);
    }

    @Override
    public void unregisterApis(String pluginId) {

        cache.get(pluginId).forEach(clazz -> {
            String name = UniqueNameUtil.getName(clazz, pluginId);
            Optional.ofNullable(restUrlScanCache.get(name)).ifPresent(restUrlScanner -> {
                restUrlScanner.unregister();
                restUrlScanCache.remove(name);
            });
        });
        cache.remove(pluginId);
    }
}
