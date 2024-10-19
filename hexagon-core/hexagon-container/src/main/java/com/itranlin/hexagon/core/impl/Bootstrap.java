package com.itranlin.hexagon.core.impl;

import com.itranlin.hexagon.classloader.PluginMetaConfig;
import com.itranlin.hexagon.classloader.PluginMetaService;
import com.itranlin.hexagon.core.HexagonAppContext;
import com.itranlin.hexagon.core.ObjectStore;
import com.itranlin.hexagon.exception.HexagonInitException;
import com.itranlin.hexagon.spi.HexagonAppContextSpiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Bootstrap.
 */
@SuppressWarnings("unused")
public class Bootstrap {

    private final static Logger log = LoggerFactory.getLogger(Bootstrap.class);

    /**
     * Bootstrap hexagon app context.
     *
     * @param path    插件目录
     * @param workDir 工作目录
     * @return hexagon app context
     * @throws Throwable throwable
     */
    public static HexagonAppContext bootstrap(String path, String workDir) throws Throwable {
        return bootstrap(new SimpleObjectStore(), path, workDir, true);
    }

    /**
     * Bootstrap hexagon app context.
     *
     * @param path       插件目录
     * @param workDir    工作目录
     * @param autoDelete 自动删除已安装过的插件
     * @return hexagon app context
     * @throws Throwable throwable
     */
    public static HexagonAppContext bootstrap(String path, String workDir, Boolean autoDelete) throws Throwable {
        return bootstrap(new SimpleObjectStore(), path, workDir, autoDelete);
    }

    /**
     * Bootstrap hexagon app context.
     *
     * @param callback   对象存储仓库
     * @param path       插件目录
     * @param workDir    工作目录
     * @param autoDelete 自动删除已安装过的插件
     * @return hexagon app context
     * @throws Throwable throwable
     */
    public static HexagonAppContext bootstrap(ObjectStore callback, String path, String workDir, Boolean autoDelete) throws Throwable {

        HexagonAppContext hexagonAppContext = HexagonAppContextSpiFactory.getFirst();
        if (hexagonAppContext instanceof HexagonAppContextImpl spi) {
            spi.setObjectStore(callback);
            PluginMetaService metaService = PluginMetaService.getSpi();
            PluginMetaConfig pluginMetaConfig = new PluginMetaConfig(workDir, autoDelete);
            metaService.setConfig(pluginMetaConfig);
            spi.setPluginMetaService(metaService);
        }
        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                try {
                    if (!Files.exists(file)) {
                        return FileVisitResult.CONTINUE;
                    }
                    if (Files.isHidden(file)) {
                        return FileVisitResult.CONTINUE;

                    }
                    log.debug("准备安装插件, 压缩包路径: {}", file.getFileName());
                    hexagonAppContext.install(file.toFile());
                } catch (Exception e) {
                    log.warn("插件包 [{}] 无效！", file.getFileName());
                    log.trace("{} ---->>>> {}", e.getMessage(), file.getFileName(), e);
                } catch (Throwable e) {
                    throw new HexagonInitException(e);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        log.debug("安装结束, 插件列表:{}", hexagonAppContext.getAllPluginId());
        return hexagonAppContext;

    }
}
