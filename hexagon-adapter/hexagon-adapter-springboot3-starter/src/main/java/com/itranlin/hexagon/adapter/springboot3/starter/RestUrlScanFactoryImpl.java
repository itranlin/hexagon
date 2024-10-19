package com.itranlin.hexagon.adapter.springboot3.starter;

import com.itranlin.hexagon.adapter.springboot.common.RestUrlScanFactory;
import com.itranlin.hexagon.adapter.springboot.common.RestUrlScanner;
import net.dreamlu.mica.auto.annotation.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * Rest接口的扫描工厂.
 */
@AutoService(RestUrlScanFactory.class)
@SuppressWarnings("unused")
public class RestUrlScanFactoryImpl implements RestUrlScanFactory {

    private final static Logger log = LoggerFactory.getLogger(RestUrlScanFactoryImpl.class);

    @Override
    public RestUrlScanner create(Object bean, Object handlerMapping,
                                 Object handlerAdapter,
                                 String pluginId, Supplier<String> replaceEnabled) {
        return new RestUrlScannerImpl(
                (RequestMappingHandlerMapping) handlerMapping,
                (RequestMappingHandlerAdapter) handlerAdapter,
                bean, pluginId);
    }

    /**
     * Rest接口扫描器实现.
     */
    static class RestUrlScannerImpl implements RestUrlScanner {

        private final RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();

        private final RequestMappingHandlerMapping handlerMapping;
        private final RequestMappingHandlerAdapter handlerAdapter;
        private final Object obj;
        private final String pluginId;
        private List<RequestMappingInfoWrapper> scan;


        /**
         * 初始化扫描器
         *
         * @param handlerMapping handler mapping
         * @param handlerAdapter handler adapter
         * @param bean           bean
         * @param pluginId       plugin id
         */
        public RestUrlScannerImpl(RequestMappingHandlerMapping handlerMapping,
                                  RequestMappingHandlerAdapter handlerAdapter,
                                  Object bean,
                                  String pluginId) {
            this.handlerMapping = handlerMapping;
            this.handlerAdapter = handlerAdapter;
            this.obj = bean;
            this.pluginId = pluginId;
            this.config.setContentNegotiationManager(handlerMapping.getContentNegotiationManager());
            this.config.setPatternParser(handlerMapping.getPatternParser());
            this.config.setPathMatcher(handlerMapping.getPathMatcher());
        }

        @Override
        public void register() {
            scan = scan(obj.getClass());
            for (RequestMappingInfoWrapper mapping : scan) {
                log.trace("覆盖, 删除老的 URL {} {}", pluginId, (mapping.path));
                handlerMapping.unregisterMapping(mapping.requestMappingInfo);
                handlerAdapter.afterPropertiesSet();
                handlerMapping.registerMapping(mapping.requestMappingInfo, obj, mapping.method);
                log.trace("注册 url, mapping = {}", (mapping.path));
            }
            handlerAdapter.afterPropertiesSet();
        }

        @Override
        public void unregister() {
            for (RequestMappingInfoWrapper mapping : scan) {
                handlerMapping.unregisterMapping(mapping.requestMappingInfo);
                log.trace("反注册 url, mapping = {}", (mapping.path));
            }
            handlerAdapter.afterPropertiesSet();
        }

        /**
         * 扫描Rest.
         *
         * @param clazz 当前扫描的clazz
         * @return Rest接口
         */
        public List<RequestMappingInfoWrapper> scan(Class<?> clazz) {
            List<RequestMappingInfoWrapper> result = new ArrayList<>();
            Annotation[] annotations = clazz.getDeclaredAnnotations();
            if (clazz.getName().contains("$$EnhancerByCGLIB$$")) {
                annotations = clazz.getSuperclass().getDeclaredAnnotations();
                clazz = clazz.getSuperclass();
            }
            for (Annotation annotation : annotations) {
                String[] parentPath = new String[]{""};
                if (annotation.annotationType().equals(RestController.class) || annotation.annotationType().equals(Controller.class)) {
                    if (clazz.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping mapping = clazz.getAnnotation(RequestMapping.class);
                        parentPath = mapping.value();
                    }

                    Method[] declaredMethods = clazz.getDeclaredMethods();
                    for (Method declaredMethod : declaredMethods) {
                        boolean isPublic = Modifier.isPublic(declaredMethod.getModifiers());
                        if (isPublic) {
                            Annotation[] an = declaredMethod.getDeclaredAnnotations();
                            for (Annotation ann : an) {
                                if (ann.annotationType().equals(RequestMapping.class)) {
                                    RequestMapping mapping = declaredMethod.getAnnotation(RequestMapping.class);
                                    RequestMethod[] method = mapping.method();
                                    String[] paths = RequestMappingInfoWrapper.buildPath(parentPath, mapping.value());
                                    for (String path : paths) {
                                        result.add(new RequestMappingInfoWrapper(RequestMappingInfo
                                                .paths(path)
                                                .methods(method)
                                                .params(mapping.params())
                                                .options(this.config)
                                                .headers(mapping.headers())
                                                .build(), declaredMethod, path));
                                    }
                                }
                                if (ann.annotationType().equals(GetMapping.class)) {
                                    GetMapping mapping = declaredMethod.getAnnotation(GetMapping.class);
                                    String[] paths = RequestMappingInfoWrapper.buildPath(parentPath, mapping.value());
                                    for (String path : paths) {
                                        result.add(new RequestMappingInfoWrapper(RequestMappingInfo
                                                .paths(path)
                                                .methods(RequestMethod.GET)
                                                .params(mapping.params())
                                                .options(this.config)
                                                .headers(mapping.headers())
                                                .build(), declaredMethod, path));
                                    }
                                }

                                if (ann.annotationType().equals(PostMapping.class)) {
                                    PostMapping mapping = declaredMethod.getAnnotation(PostMapping.class);
                                    String[] paths = RequestMappingInfoWrapper.buildPath(parentPath, mapping.value());
                                    for (String path : paths) {
                                        result.add(new RequestMappingInfoWrapper(
                                                RequestMappingInfo
                                                        .paths(path)
                                                        .methods(RequestMethod.POST)
                                                        .params(mapping.params())
                                                        .options(this.config)
                                                        .headers(mapping.headers())
                                                        .build(), declaredMethod, path)
                                        );
                                    }
                                }

                                if (ann.annotationType().equals(PutMapping.class)) {
                                    PutMapping mapping = declaredMethod.getAnnotation(PutMapping.class);
                                    String[] paths = RequestMappingInfoWrapper.buildPath(parentPath, mapping.value());
                                    for (String path : paths) {
                                        result.add(new RequestMappingInfoWrapper(
                                                RequestMappingInfo
                                                        .paths(path)
                                                        .methods(RequestMethod.PUT)
                                                        .params(mapping.params())
                                                        .options(this.config)
                                                        .headers(mapping.headers())
                                                        .build(), declaredMethod, path)
                                        );
                                    }
                                }

                                if (ann.annotationType().equals(DeleteMapping.class)) {
                                    DeleteMapping mapping = declaredMethod.getAnnotation(DeleteMapping.class);
                                    String[] paths = RequestMappingInfoWrapper.buildPath(parentPath, mapping.value());
                                    for (String path : paths) {
                                        result.add(new RequestMappingInfoWrapper(
                                                RequestMappingInfo
                                                        .paths(path)
                                                        .options(this.config)
                                                        .methods(RequestMethod.DELETE)
                                                        .params(mapping.params())
                                                        .headers(mapping.headers())
                                                        .build(), declaredMethod, path)
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return result;
        }
    }

    /**
     * Rest接口处理器.
     */
    record RequestMappingInfoWrapper(RequestMappingInfo requestMappingInfo, Method method, String path) {

        /**
         * 格式化path.
         *
         * @param classPath  Class定义的path
         * @param methodPath Method定义的path
         * @return Rest列表
         */
        public static String[] buildPath(String[] classPath, String[] methodPath) {
            return Arrays.stream(classPath).map((parent) -> {
                if (methodPath.length == 0) {
                    return new String[]{parent};
                }
                var paths = new String[methodPath.length];
                for (int i = 0; i < methodPath.length; i++) {
                    if (methodPath[0].startsWith("/")) {
                        paths[i] = parent + methodPath[i];
                    }
                    paths[i] = parent + "/" + methodPath[i];

                }
                return paths;
            }).flatMap(Arrays::stream).toArray(String[]::new);

        }
    }
}
