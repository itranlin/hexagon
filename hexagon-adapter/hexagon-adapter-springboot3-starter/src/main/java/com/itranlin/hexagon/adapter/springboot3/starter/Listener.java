package com.itranlin.hexagon.adapter.springboot3.starter;

import com.itranlin.hexagon.adapter.springboot.common.DocHandler;
import net.dreamlu.mica.auto.annotation.AutoRunListener;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.Duration;

/**
 * 启动监听.
 */
@AutoRunListener
@SuppressWarnings("unused")
public class Listener implements SpringApplicationRunListener {

    @Override
    public void started(ConfigurableApplicationContext context, Duration timeTaken) {
        SpringApplicationRunListener.super.started(context, timeTaken);
        DocHandler.builder().context(context).build().init();
    }
}
