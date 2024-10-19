package com.itranlin.hexagon.core.impl.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

/**
 * Hexagon plugin interceptor.
 */
public class HexagonPluginInterceptor implements MethodInterceptor {

    private final static Logger log = LoggerFactory.getLogger(HexagonPluginInterceptor.class);
    private static final ThreadLocal<Stack<String>> MDC_STACK = ThreadLocal.withInitial(Stack::new);

    private final String pluginId;
    private final Object target;


    /**
     * Instantiates a new Hexagon plugin interceptor.
     *
     * @param pluginId 插件ID
     * @param target   the target
     */
    public HexagonPluginInterceptor(String pluginId, Object target) {
        this.pluginId = pluginId;
        this.target = target;
    }

    private static void pushPluginId(String pluginId) {
        MDC_STACK.get().push(pluginId);
    }

    private static void popPluginId() {
        MDC_STACK.get().pop();
    }

    /**
     * Need enhancer boolean.
     *
     * @param o the o
     * @return the boolean
     */
    public static boolean needEnhancer(Object o) {
        return !(o instanceof HexagonIntercept);
    }

    /**
     * Create enhancer object.
     *
     * @param bean     SpringBean
     * @param pluginId 插件ID
     * @return 代理对象
     */
    public static Object createEnhancer(Object bean, String pluginId) {
        if (!needEnhancer(bean)) {
            return bean;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        enhancer.setInterfaces(new Class[]{HexagonIntercept.class});
        enhancer.setNamingPolicy(new HexagonNamingPolicy(pluginId));
        enhancer.setCallback(new HexagonPluginInterceptor(pluginId, bean));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object origin, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        try {
            Class<?> declaringClass = method.getDeclaringClass();
            if (!declaringClass.equals(origin.getClass()) || method.getAnnotation(HexagonNoProxy.class) != null) {
                return method.invoke(target, objects);
            }
            try {
                pushPluginId(pluginId);
                method.setAccessible(true);
                return method.invoke(target, objects);
            } finally {
                popPluginId();
            }
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}