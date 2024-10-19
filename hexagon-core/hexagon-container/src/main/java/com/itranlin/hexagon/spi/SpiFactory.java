package com.itranlin.hexagon.spi;

import java.util.*;

/**
 * The type Spi factory.
 */
@SuppressWarnings("unchecked")
public class SpiFactory {

    private static final Map<Class<?>, Object> cache = new HashMap<>();
    private static final Map<Class<?>, List<Object>> cacheList = new HashMap<>();

    /**
     * 获取一个Spi实例.
     *
     * @param <T>   Spi类型
     * @param clazz Spi类型Class
     * @return Spi实例
     */
    public static <T> T get(Class<T> clazz) {
        return get(clazz, null);
    }

    /**
     * 获取 Spi列表.
     *
     * @param <T> Spi类型
     * @param c   Spi类型Class
     * @return Spi实例列表
     */
    public static <T> List<T> getList(Class<T> c) {
        if (!cacheList.containsKey(c)) {
            synchronized (SpiFactory.class) {
                if (!cacheList.containsKey(c)) {
                    ServiceLoader<T> load = ServiceLoader.load(c);
                    List<Object> list = new ArrayList<>();
                    cacheList.put(c, list);
                    for (T obj : load) {
                        list.add(obj);
                    }
                }
            }
        }
        return (List<T>) cacheList.get(c);
    }

    /**
     * Get t.
     *
     * @param <T> the type parameter
     * @param c   the c
     * @param d   the d
     * @return the t
     */
    public static <T> T get(Class<T> c, T d) {
        if (!cache.containsKey(c)) {
            synchronized (SpiFactory.class) {
                if (!cache.containsKey(c)) {
                    ServiceLoader<T> load = ServiceLoader.load(c);
                    for (T obj : load) {
                        cache.put(c, obj);
                    }
                }
            }
        }
        return Optional.ofNullable((T) cache.get(c)).orElse(d);
    }
}
