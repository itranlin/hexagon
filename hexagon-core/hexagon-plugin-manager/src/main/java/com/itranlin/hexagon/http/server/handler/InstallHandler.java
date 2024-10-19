package com.itranlin.hexagon.http.server.handler;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.Method;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.action.Action;
import com.itranlin.hexagon.core.HexagonAppContext;
import com.itranlin.hexagon.core.Plugin;
import com.itranlin.hexagon.json.HexagonJson;
import com.itranlin.hexagon.spi.SpiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * 安装插件.
 */
public class InstallHandler implements Action {
    /**
     * Hexagon app context.
     */
    static final HexagonAppContext HEXAGON_APP_CONTEXT = SpiFactory.get(HexagonAppContext.class);
    private final static Logger log = LoggerFactory.getLogger(InstallHandler.class);
    /**
     * Hexagon json.
     */
    final HexagonJson hexagonJson = SpiFactory.get(HexagonJson.class);

    @Override
    public void doAction(HttpServerRequest request, HttpServerResponse response) throws HttpException {
        if (!request.getMethod().equals(Method.GET.name())) {
            response.send(HttpStatus.HTTP_BAD_METHOD);
            response.write("need post method");
            return;
        }
        String path = request.getParam("path");
        try {
            Plugin pluginInfo = HEXAGON_APP_CONTEXT.install(Paths.get(path).toFile());
            response.sendOk();
            response.write(hexagonJson.toString(pluginInfo));
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            response.send(HttpStatus.HTTP_INTERNAL_ERROR);
            response.write(e.getMessage());
        }
    }
}
