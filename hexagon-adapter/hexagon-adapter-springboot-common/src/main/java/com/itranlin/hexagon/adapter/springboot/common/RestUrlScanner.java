package com.itranlin.hexagon.adapter.springboot.common;

/**
 * Rest接口扫描器接口.
 */
public interface RestUrlScanner {

    /**
     * 注册接口.
     */
    void register();

    /**
     * 卸载接口.
     */
    void unregister();
}
