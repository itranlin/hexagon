package com.itranlin.hexagon.document.impl;


import com.itranlin.hexagon.document.api.HexagonExtApiInterface;
import com.itranlin.hexagon.document.api.model.ExtDocInterface;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Boot.
 */
public class DocScanner {

    /**
     * 扫描 list.
     *
     * @param basePackage 扫描基础包
     * @return the list
     */
    public static synchronized List<ExtDocInterface> scan(String basePackage) {
        List<Class<?>> classes = new ArrayList<>();
        try (ScanResult scanResult = new ClassGraph().enableAllInfo().acceptPackages(basePackage).scan()) {
            for (ClassInfo classInfo : scanResult.getClassesWithAnnotation(HexagonExtApiInterface.class.getName())) {
                Class<?> classObj = classInfo.loadClass();
                classes.add(classObj);
            }
        }
        DocParser docParser = new DefaultDocParser();
        return classes.stream().map(docParser::parse).toList();
    }
}
