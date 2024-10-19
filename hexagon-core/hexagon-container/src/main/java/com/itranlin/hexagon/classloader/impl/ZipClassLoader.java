package com.itranlin.hexagon.classloader.impl;

import cn.hutool.core.util.ZipUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.zip.ZipFile;

/**
 * ZipClassLoader.
 */
public class ZipClassLoader extends PluginClassLoader {

    /**
     * 实例化ZipClassLoader
     *
     * @param zipFile     Zip文件
     * @param extractPath 展开路径
     * @param parent      父ClassLoader
     * @throws Exception 处理异常
     */
    public ZipClassLoader(File zipFile, Path extractPath, ClassLoader parent) throws Exception {
        super(extractPath, parent);
        try (ZipFile file = new ZipFile(zipFile)) {
            ZipUtil.unzip(file, extractPath.toFile());
        }
    }
}
