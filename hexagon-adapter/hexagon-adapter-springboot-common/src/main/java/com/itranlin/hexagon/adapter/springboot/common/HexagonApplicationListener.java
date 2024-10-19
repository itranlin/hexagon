package com.itranlin.hexagon.adapter.springboot.common;

import com.itranlin.hexagon.adapter.springboot.common.config.PluginMasterConfigBean;
import com.itranlin.hexagon.core.PluginManager;
import com.itranlin.hexagon.core.impl.Bootstrap;
import com.itranlin.hexagon.core.impl.proxy.HexagonPluginInterceptor;
import com.itranlin.hexagon.exception.HexagonInitException;
import com.itranlin.hexagon.spi.SpiFactory;
import com.itranlin.hexagon.utils.StringUtil;
import com.itranlin.hexagon.utils.UniqueNameUtil;
import net.dreamlu.mica.auto.annotation.AutoListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.Optional;

/**
 * HexagonApplicationListener.
 */
@AutoListener
@SuppressWarnings("unused")
public class HexagonApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(HexagonApplicationListener.class);

    private static final BeanPostProcessor BEAN_POST_PROCESSOR = new BeanPostProcessor() {
        @Override
        public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
            String pluginId = UniqueNameUtil.getPluginId(beanName);
            if (StringUtil.isEmpty(pluginId)) {
                return bean;
            }
            return HexagonPluginInterceptor.createEnhancer(bean, pluginId);
        }

        @Override
        public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
            return bean;
        }
    };

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        try {
            ConfigurableApplicationContext context = event.getApplicationContext();
            var config = context.getBean(PluginMasterConfigBean.class);
            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
            beanFactory.addBeanPostProcessor(BEAN_POST_PROCESSOR);
            BeanDefinitionRegistry registry = (BeanDefinitionRegistry) context;
            if (ensureNotReady(beanFactory)) {
                return;
            }
            if (context instanceof GenericApplicationContext) {
                ((GenericApplicationContext) context).setAllowBeanDefinitionOverriding(true);
            }
            ObjectStoreSpringboot objectStore = new ObjectStoreSpringboot(registry, beanFactory);

            Bootstrap.bootstrap(objectStore, config.getPath(), config.getWorkDir(), config.getAutoDelete());
            Optional.ofNullable(SpiFactory.get(PluginManager.class)).ifPresent(PluginManager::start);
        } catch (Throwable e) {
            throw new HexagonInitException(e);
        }
    }

    private boolean ensureNotReady(ConfigurableListableBeanFactory beanFactory) {
        Iterator<String> beanNamesIterator = beanFactory.getBeanNamesIterator();
        while (beanNamesIterator.hasNext()) {
            String next = beanNamesIterator.next();
            if ("servletContext".equals(next)) {
                return false;
            }
        }
        log.warn("springboot not ready.....");
        return true;
    }
}
