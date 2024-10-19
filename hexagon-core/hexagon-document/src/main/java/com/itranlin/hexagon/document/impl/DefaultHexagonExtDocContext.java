package com.itranlin.hexagon.document.impl;


import com.itranlin.hexagon.document.api.HexagonDocContext;
import com.itranlin.hexagon.document.api.model.ExtDocInterface;
import net.dreamlu.mica.auto.annotation.AutoService;

import java.util.List;

/**
 * The type Default hexagon ext doc context.
 */
@AutoService(HexagonDocContext.class)
@SuppressWarnings("unused")
public class DefaultHexagonExtDocContext implements HexagonDocContext {

    private volatile List<ExtDocInterface> list;

    @Override
    public synchronized List<ExtDocInterface> getExtDocList(String basePackage) {
        if (list == null) {
            list = DocScanner.scan(basePackage);
        }
        return list;
    }

}
