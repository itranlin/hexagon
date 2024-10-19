package com.itranlin.hexagon.spi;

import com.itranlin.hexagon.core.HexagonAppContext;

/**
 * The type Hexagon app context spi factory.
 */
public class HexagonAppContextSpiFactory {

    /**
     * 获取一个Spi实例.
     *
     * @return Spi实例
     */
    public static HexagonAppContext getFirst() {
        return SpiFactory.get(HexagonAppContext.class);
    }
}
