package com.itranlin.hexagon.classloader.support;

import com.itranlin.hexagon.classloader.impl.JarClassLoader;
import com.itranlin.hexagon.classloader.impl.PluginClassLoader;
import com.itranlin.hexagon.classloader.impl.ZipClassLoader;
import com.itranlin.hexagon.exception.HexagonInitException;
import com.itranlin.hexagon.exception.HexagonNoSupportFileTypeException;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ClassLoaderFinder
 */
public class ClassLoaderFinder {

    /**
     * 获取ClassLoader.
     *
     * @param file 文件
     * @param dir  目录
     * @return ClassLoader
     */
    public static PluginClassLoader find(File file, String dir) {
        try {
            ClassLoader parent = Thread.currentThread().getContextClassLoader();

            String fileExtension = FilenameUtils.getExtension(file.getName());
            Path path = Paths.get(dir);

            return switch (fileExtension) {
                case "jar" -> new JarClassLoader(file, path, parent);
                case "zip" -> new ZipClassLoader(file, path, parent);
                default -> throw new HexagonNoSupportFileTypeException("fileName = " + file.getName());
            };
        } catch (Exception e) {
            throw new HexagonInitException(e);
        }
    }
}
