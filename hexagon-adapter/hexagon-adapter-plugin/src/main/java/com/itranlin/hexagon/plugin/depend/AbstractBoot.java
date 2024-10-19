package com.itranlin.hexagon.plugin.depend;

import com.itranlin.hexagon.classloader.impl.PluginClassLoader;
import com.itranlin.hexagon.core.HexagonBoot;
import com.itranlin.hexagon.core.scanner.PluginObjectScanner;
import com.itranlin.hexagon.exception.HexagonInitException;
import com.itranlin.hexagon.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象启动类.
 */
@SuppressWarnings("unused")
public abstract class AbstractBoot implements HexagonBoot {

    private final static Logger log = LoggerFactory.getLogger(AbstractBoot.class);

    private final PluginObjectScanner pluginBeanRegister;

    /**
     * 启动类初始值.
     */
    public AbstractBoot() {
        if (!(getClass().getClassLoader() instanceof PluginClassLoader)) {
            throw new HexagonInitException("classLocation 非法");
        }
        String classLocation = ((PluginClassLoader) getClass().getClassLoader()).getPath();
        ClassLoader pluginClassLoader = getClass().getClassLoader();
        this.pluginBeanRegister = new PluginObjectScanner();
        this.pluginBeanRegister.setPluginClassLoader(pluginClassLoader);
        this.pluginBeanRegister.setLocation(classLocation);
        String scanPath = getPackagePath();
        if (StringUtil.isEmpty(scanPath)) {
            throw new HexagonInitException("AbstractBoot getScanPath 不能为空.");
        }
        this.pluginBeanRegister.setScanPath(getPackagePath());
    }


    /**
     * 获得扫描器.
     *
     * @return 扫描器
     */
    @Override
    public PluginObjectScanner getScanner() {
        return this.pluginBeanRegister;
    }

    /**
     * 获得当前的包路径.
     *
     * @return 包路径
     */
    protected String getPackagePath() {
        return getClass().getPackage().getName();
    }

}
