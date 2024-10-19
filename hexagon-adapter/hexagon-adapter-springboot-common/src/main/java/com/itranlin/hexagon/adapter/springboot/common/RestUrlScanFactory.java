package com.itranlin.hexagon.adapter.springboot.common;

import com.itranlin.hexagon.spi.SpiFactory;

import java.util.function.Supplier;

/**
 * RestUrl扫描工厂（SPI）.
 */
public interface RestUrlScanFactory {

    /**
     * 获得工厂实例
     *
     * @return 工厂实例
     */
    static RestUrlScanFactory getInstance() {
        return SpiFactory.get(RestUrlScanFactory.class);
    }

    /**
     * 创建扫描器.
     *
     * @param bean                         需要扫描的Bean
     * @param requestMappingHandlerMapping Spring的requestMappingHandlerMapping
     * @param requestMappingHandlerAdapter Spring的requestMappingHandlerAdapter
     * @param pluginId                     插件ID
     * @param replaceEnabled               路径替换
     * @return 扫描器
     */
    RestUrlScanner create(Object bean,
                          Object requestMappingHandlerMapping,
                          Object requestMappingHandlerAdapter,
                          String pluginId,
                          Supplier<String> replaceEnabled);
}
