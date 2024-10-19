package com.itranlin.hexagon.core.scanner;

import com.itranlin.hexagon.exception.HexagonInitException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Plugin object scanner.
 */
public class PluginObjectScanner {

    private String scanPath;
    private ClassLoader pluginClassLoader;
    private String location;


    /**
     * Scan list.
     *
     * @return the list
     */
    public List<Class<?>> scan() {
        try {
            Path path = Paths.get(location);
            return ClassesScanner.doScan(path.toFile(), scanPath, pluginClassLoader);
        } catch (Throwable e) {
            throw new HexagonInitException(e);
        }
    }

    /**
     * 设置 scan path.
     *
     * @param scanPath the scan path
     */
    public void setScanPath(String scanPath) {
        this.scanPath = scanPath;
    }

    /**
     * 设置 plugin class loader.
     *
     * @param pluginClassLoader the plugin class loader
     */
    public void setPluginClassLoader(ClassLoader pluginClassLoader) {
        this.pluginClassLoader = pluginClassLoader;
    }

    /**
     * 设置 location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
