package com.itranlin.hexagon.http.server.handler;

import cn.hutool.core.net.multipart.UploadFile;
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

import java.io.File;
import java.util.UUID;

/**
 * 上传并安装插件.
 */
public class HttpFileHandler implements Action {
    /**
     * Hexagon app context.
     */
    static final HexagonAppContext HEXAGON_APP_CONTEXT = SpiFactory.get(HexagonAppContext.class);
    private final static Logger log = LoggerFactory.getLogger(HttpFileHandler.class);
    /**
     * Hexagon json.
     */
    final HexagonJson hexagonJson = SpiFactory.get(HexagonJson.class);

    @Override
    public void doAction(HttpServerRequest request, HttpServerResponse response) {
        if (!request.getMethod().contains(Method.POST.name())) {
            response.send(HttpStatus.HTTP_BAD_METHOD);
            response.write("need post method");
            return;
        }
        try {
            final UploadFile file = request.getMultipart().getFile("file");
            File pluginTmp = file.write(System.getProperty("java.io.tmpdir") + UUID.randomUUID());
            Plugin pluginInfo = HEXAGON_APP_CONTEXT.install(pluginTmp);
            response.send(HttpStatus.HTTP_OK);
            response.write(hexagonJson.toString(pluginInfo));
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            response.send(HttpStatus.HTTP_INTERNAL_ERROR);
            response.write(e.getMessage());
        }
    }
}
