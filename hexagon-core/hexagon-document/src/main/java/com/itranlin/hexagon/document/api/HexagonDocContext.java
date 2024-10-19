package com.itranlin.hexagon.document.api;


import com.itranlin.hexagon.document.api.model.ExtDocInterface;
import com.itranlin.hexagon.spi.SpiFactory;

import java.util.List;

/**
 * The interface Hexagon doc context.
 */
public interface HexagonDocContext {

    /**
     * 获取 HexagonDocContext spi实例.
     *
     * @return spi实例
     */
    static HexagonDocContext getSpi() {
        return SpiFactory.get(HexagonDocContext.class);
    }

    /**
     * 获取 ext doc list.
     *
     * @param basePackage 扫描基础包
     * @return ext doc list
     */
    List<ExtDocInterface> getExtDocList(String basePackage);
}
