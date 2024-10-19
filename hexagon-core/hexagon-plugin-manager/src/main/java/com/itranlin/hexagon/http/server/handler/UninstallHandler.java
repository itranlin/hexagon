package com.itranlin.hexagon.http.server.handler;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.Method;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.action.Action;
import com.itranlin.hexagon.core.HexagonAppContext;
import com.itranlin.hexagon.spi.SpiFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 卸载插件.
 */
public class UninstallHandler implements Action {
    /**
     * Hexagon app context.
     */
    static final HexagonAppContext HEXAGON_APP_CONTEXT = SpiFactory.get(HexagonAppContext.class);
    private final static Logger log = LoggerFactory.getLogger(UninstallHandler.class);

    @Override
    public void doAction(HttpServerRequest request, HttpServerResponse response) throws HttpException {
        if (!request.getMethod().equals(Method.GET.name())) {
            response.send(HttpStatus.HTTP_BAD_METHOD);
            response.write("need post method");
            return;
        }
        String pluginId = request.getParam("pluginId");
        try {
            List<String> allPluginId = HEXAGON_APP_CONTEXT.getAllPluginId();
            if (allPluginId.contains(pluginId)) {
                HEXAGON_APP_CONTEXT.uninstall(pluginId);
                response.write("卸载成功：" + pluginId);
            } else {
                response.write("没有这个插件：" + pluginId);
            }
            response.sendOk();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.send(HttpStatus.HTTP_INTERNAL_ERROR);
            response.write(e.getMessage());
        }
    }
}
