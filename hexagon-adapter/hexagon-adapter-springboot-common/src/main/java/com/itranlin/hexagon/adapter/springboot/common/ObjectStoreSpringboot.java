package com.itranlin.hexagon.adapter.springboot.common;

import com.itranlin.hexagon.adapter.springboot.common.spi.ObjectScanner;
import com.itranlin.hexagon.core.ObjectStore;
import com.itranlin.hexagon.core.impl.PluginLifeCycleHookManager;
import com.itranlin.hexagon.spi.SpiFactory;
import com.itranlin.hexagon.utils.StringUtil;
import com.itranlin.hexagon.utils.UniqueNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * springboot对象存储仓库.
 */
@SuppressWarnings("unchecked")
public class ObjectStoreSpringboot implements ObjectStore {

    private final static Logger log = LoggerFactory.getLogger(ObjectStoreSpringboot.class);

    private final BeanDefinitionRegistry beanDefinitionRegistry;

    private final ConfigurableListableBeanFactory beanFactory;

    private final Map<String/* pluginId */, List<Class<?>>> classesCache = new HashMap<>();

    private final List<ObjectScanner> objectScanners;

    /**
     * 构造对象存储仓库
     *
     * @param beanDefinitionRegistry Spring的Bean定义注册器
     * @param beanFactory            Bean工厂
     */
    public ObjectStoreSpringboot(BeanDefinitionRegistry beanDefinitionRegistry, ConfigurableListableBeanFactory beanFactory) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
        this.beanFactory = beanFactory;
        objectScanners = SpiFactory.getList(ObjectScanner.class);
        Runtime.getRuntime().addShutdownHook(new ShutdownThread(() -> new HashMap<>(classesCache).keySet().forEach(this::unregisterCallback)));
    }

    /**
     * 注册插件到对象仓库
     *
     * @param classes     插件里的核心 class, hexagonBean 标记的 class.
     * @param pluginId 插件 id;
     */
    @Override
    public void registerCallback(List<Class<?>> classes, String pluginId) {
        List<Class<?>> rollbackList = new ArrayList<>();
        objectScanners.forEach(e -> e.init(this));
        for (Class<?> clazz : classes) {
            String name = UniqueNameUtil.getName(clazz, pluginId);
            try {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
                BeanDefinition beanDefinition = builder.getBeanDefinition();
                beanDefinitionRegistry.registerBeanDefinition(name, beanDefinition);
                rollbackList.add(clazz);
                log.trace("------->>>> 插件 register beanName:{}", name);
            } catch (Exception e) {
                log.error("插件安装失败, 回滚中......{}", e.getMessage(), e);
                unregisterCallbackAll(pluginId, e, rollbackList);
                throw e;
            }
        }

        objectScanners.forEach(e -> e.registerApis(classes, pluginId));
        postInit(pluginId, classes);
        classesCache.put(pluginId, classes);
    }

    /**
     * 卸载Class
     * @param pluginId 插件ID
     * @param ex 发生的异常
     * @param rollbackList 需要卸载的列表
     */
    private void unregisterCallbackAll(String pluginId, Exception ex, List<Class<?>> rollbackList) {
        for (Class<?> a : rollbackList) {
            try {
                unregisterCallback(a, pluginId);
            } catch (Exception exception) {
                log.error("卸载callback 失败....{}", ex.getMessage(), ex);
            }
        }
    }


    /**
     * 从对象仓库将这个插件的东西全部删除.
     *
     * @param pluginId 插件ID
     */
    @Override
    public void unregisterCallback(String pluginId) {

        List<Class<?>> classes = classesCache.get(pluginId);
        if (classes == null) {
            return;
        }

        postDestroy(pluginId, classes);
        objectScanners.forEach(e -> e.unregisterApis(pluginId));
        classesCache.remove(pluginId);
    }

    /**
     * 从对象仓库获取某个对象
     *
     * @param <T> 对象类型
     * @param pluginId  插件ID
     * @param clazz 对象类型
     * @return 对象
     */
    @Override
    public <T> T getObject(Class<?> clazz, String pluginId) {
        if (beanFactory == null) {
            return null;
        }
        if (StringUtil.isEmpty(pluginId)) {
            return (T) beanFactory.getBean(clazz);
        }
        String name = UniqueNameUtil.getName(clazz, pluginId);
        Object t = beanFactory.getBean(name);
        return (T) t;
    }

    /**
     * 获取原始对象
     *
     * @return 原始对象
     */
    @Override
    public Object getOrigin() {
        return beanFactory;
    }



    /**
     * 卸载C
     * @param pluginId 插件ID
     * @param clazz class
     */
    private void unregisterCallback(Class<?> clazz, String pluginId) {
        String name = UniqueNameUtil.getName(clazz, pluginId);
        beanDefinitionRegistry.removeBeanDefinition(name);
        log.debug("------->>>> 卸载插件，移除Bean:{}", name);
    }


    private void postInit(String pluginId, List<Class<?>> classes) {

        classes.forEach(clazz -> {
            String name = UniqueNameUtil.getName(clazz, pluginId);
            try {
                beanFactory.getBean(name);
                PluginLifeCycleHookManager.postInit(this, clazz, () -> beanFactory.getBean(name), name);
            } catch (Exception ignored) {
            }
        });
    }

    private void postDestroy(String pluginId, List<Class<?>> classes) {
        classes.forEach(clazz -> {
            unregisterCallback(clazz, pluginId);
            PluginLifeCycleHookManager.postDestroy(this, clazz, UniqueNameUtil.getName(clazz, pluginId));
        });
    }

    /**
     * 容器关闭时回调线程.
     */
    static class ShutdownThread extends Thread {

        /**
         * 容器关闭时回调线程.
         *
         * @param runnable 执行方法
         */
        public ShutdownThread(Runnable runnable) {
            super(runnable, "hexagon-stop");
        }
    }
}
