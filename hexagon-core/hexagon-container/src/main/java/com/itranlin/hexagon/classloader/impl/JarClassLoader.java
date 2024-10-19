package com.itranlin.hexagon.classloader.impl;

import cn.hutool.core.util.ZipUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.jar.JarFile;

/**
 * JarClassLoader
 */
public class JarClassLoader extends PluginClassLoader {

    /**
     * JarClassLoader实例.
     *
     * @param jarFile     jar文件
     * @param extractPath 展开路径
     * @param parent      父Classpath
     * @throws Exception 异常
     */
    public JarClassLoader(File jarFile, Path extractPath, ClassLoader parent) throws Exception {
        super(extractPath, parent);
        try (JarFile file = new JarFile(jarFile)) {
            ZipUtil.unzip(file, extractPath.toFile());
        }
    }
}
