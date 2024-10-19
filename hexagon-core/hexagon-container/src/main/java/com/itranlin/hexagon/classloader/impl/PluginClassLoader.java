package com.itranlin.hexagon.classloader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;

/**
 * PluginClassLoader.
 */
public class PluginClassLoader extends URLClassLoader {

    private static final Logger log = LoggerFactory.getLogger(PluginClassLoader.class);
    private final Path extractPath;
    /**
     * 是否需要界定扫描包.
     */
    private boolean definePackage = false;

    /**
     * PluginClassLoader实例.
     *
     * @param extractPath 展开路径
     * @param parent      父ClassLoader
     * @throws Exception 处理异常
     */
    public PluginClassLoader(Path extractPath, ClassLoader parent) throws Exception {
        super(new URL[]{extractPath.toUri().toURL()}, parent);
        this.extractPath = extractPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        Path classFilePath = extractPath.resolve(name.replace('.', '/') + ".class");
        if (!Files.exists(classFilePath)) {
            throw new ClassNotFoundException(name);
        }

        try {
            byte[] classFile = Files.readAllBytes(classFilePath);
            if (!definePackage) {
                try {
                    definePackage(name.substring(0, name.lastIndexOf(".")), null, null, null, null, null, null, null);
                } catch (Exception e) {
                    log.warn("definePackage error", e);
                }
                definePackage = true;
            }
            return defineClass(name, classFile, 0, classFile.length, new ProtectionDomain(new CodeSource(classFilePath.toUri().toURL(), (Certificate[]) null), new Permissions()));
        } catch (Exception e) {
            return getParent().loadClass(name);
        }
    }

    /**
     * 获取扫描的路径
     *
     * @return 路径
     */
    public String getPath() {
        return extractPath.toAbsolutePath().toString();
    }
}
