package com.itranlin.hexagon.http.server;

import cn.hutool.http.HttpUtil;
import cn.hutool.http.server.SimpleServer;
import com.itranlin.hexagon.http.server.handler.GetAllHandler;
import com.itranlin.hexagon.http.server.handler.HttpFileHandler;
import com.itranlin.hexagon.http.server.handler.InstallHandler;
import com.itranlin.hexagon.http.server.handler.UninstallHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 插件管理Http服务.
 */
@SuppressWarnings("unused")
public class PluginServer {

    private final static Logger log = LoggerFactory.getLogger(PluginServer.class);
    private volatile static boolean running;
    private static SimpleServer server;

    /**
     * Stop.
     */
    public static void stop() {
        running = false;
        Optional.ofNullable(server).map(SimpleServer::getRawServer).ifPresent(httpServer -> httpServer.stop(0));
    }

    /**
     * Start.
     *
     * @param port 监听端口
     */
    public static void start(int port) {
        if (running) {
            log.warn("PluginServer is running....");
            return;
        }
        String uploadAndInstall = "/plugin/upload-and-install";
        String install = "/plugin/install";
        String uninstall = "/plugin/uninstall";
        String getAll = "/plugin/get-all";

        server = HttpUtil.createServer(port)
                .addAction(uploadAndInstall, new HttpFileHandler())
                .addAction(install, new InstallHandler())
                .addAction(uninstall, new UninstallHandler())
                .addAction(getAll, new GetAllHandler());

        server.start();
        Executors.newScheduledThreadPool(1).schedule(() -> {
            String url = "http://localhost:" + port;
            log.debug("插件管理模块: 上传并安装 URL [POST]: {}{}", url, uploadAndInstall);
            log.debug("插件管理模块: 安装 URL [GET]: {}{}?path={path}", url, install);
            log.debug("插件管理模块: 卸载 URL [GET]: {}{}?pluginId={pluginId}", url, uninstall);
            log.debug("插件管理模块: 获取所有 URL [GET]: {}{}", url, getAll);
        }, 1, TimeUnit.SECONDS);

    }

}
