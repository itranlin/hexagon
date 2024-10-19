package com.itranlin.hexagon.core.scanner;

import com.itranlin.hexagon.core.HexagonBean;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Simple class scanner.
 */
public abstract class SimpleClassScanner {

    /**
     * List add class.
     *
     * @param list          全量ClassList
     * @param classLoader   class loader
     * @param fullClassName 全限定类名
     * @throws ClassNotFoundException the class not found exception
     */
    protected static void listAddClass(List<Class<?>> list, ClassLoader classLoader, String fullClassName) throws ClassNotFoundException {
        Class<?> clazz = classLoader.loadClass(fullClassName);
        if (!clazz.isInterface()) {
            Class<?>[] impls = clazz.getInterfaces();
            for (Class<?> impl : impls) {
                if (impl.getName().equals(HexagonBean.class.getName())) {
                    list.add(clazz);
                    return;
                }
            }
        }

        label:
        for (Annotation declaredAnnotation : clazz.getDeclaredAnnotations()) {
            String name = declaredAnnotation.annotationType().getName();
            switch (name) {
                case "org.springframework.web.bind.annotation.RestController":
                case "org.springframework.stereotype.Service":
                case "org.springframework.stereotype.Component":
                case "org.springframework.stereotype.Controller":
                    list.add(clazz);
                    break label;
            }
        }
    }

}
