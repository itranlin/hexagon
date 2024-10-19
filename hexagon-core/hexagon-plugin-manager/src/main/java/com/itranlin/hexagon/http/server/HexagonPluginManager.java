package com.itranlin.hexagon.http.server;

import com.itranlin.hexagon.core.PluginManager;
import net.dreamlu.mica.auto.annotation.AutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 插件管理器.
 */
@AutoService(PluginManager.class)
@SuppressWarnings("unused")
public class HexagonPluginManager implements PluginManager {
    private final static Logger log = LoggerFactory.getLogger(HexagonPluginManager.class);
    private boolean running;


    /**
     * Instantiates a new Manager plugin manager.
     */
    public HexagonPluginManager() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    @Override
    public void start() {
        if (running) {
            return;
        }
        new PMThread(() -> {
            try {
                PluginServer.start(8888);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }).start();
        running = true;
    }

    @Override
    public void stop() {
        PluginServer.stop();
    }

    /**
     * The type Pm thread.
     */
    static class PMThread extends Thread {
        /**
         * Instantiates a new Pm thread.
         *
         * @param target the target
         */
        public PMThread(Runnable target) {
            super(target, "Hexagon-Plugin-Manager");
        }
    }

}
