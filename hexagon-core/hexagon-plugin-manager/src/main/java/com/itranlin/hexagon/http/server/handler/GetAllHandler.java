package com.itranlin.hexagon.http.server.handler;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.Method;
import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import cn.hutool.http.server.action.Action;
import com.itranlin.hexagon.core.HexagonAppContext;
import com.itranlin.hexagon.json.HexagonJson;
import com.itranlin.hexagon.spi.SpiFactory;


/**
 * 获取已安装的所有插件.
 */
public class GetAllHandler implements Action {

    /**
     * Hexagon json.
     */
    final HexagonJson hexagonJson = SpiFactory.get(HexagonJson.class);
    /**
     * Hexagon app context.
     */
    final HexagonAppContext hexagonAppContext = SpiFactory.get(HexagonAppContext.class);

    @Override
    public void doAction(HttpServerRequest request, HttpServerResponse response) throws HttpException {
        if (!request.getMethod().contains(Method.GET.name())) {
            response.send(HttpStatus.HTTP_BAD_METHOD);
            response.write("need post method");
            return;
        }
        response.sendOk();
        response.write(hexagonJson.toString(hexagonAppContext.getAllPluginId()));
    }
}
