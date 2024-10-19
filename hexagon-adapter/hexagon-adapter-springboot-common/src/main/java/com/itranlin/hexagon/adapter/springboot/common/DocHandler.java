package com.itranlin.hexagon.adapter.springboot.common;

import com.itranlin.hexagon.adapter.springboot.common.config.PluginMasterConfigBean;
import com.itranlin.hexagon.document.api.HexagonDocContext;
import com.itranlin.hexagon.document.api.model.ExtDocInterface;
import com.itranlin.hexagon.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

/**
 * 扩展文档处理器.
 */
public class DocHandler {

    private static final Logger log = LoggerFactory.getLogger(DocHandler.class);

    private final ConfigurableApplicationContext context;

    private DocHandler(ConfigurableApplicationContext context) {
        this.context = context;
    }

    /**
     * 构造器.
     *
     * @return 构造器Builder
     */
    public static DocHandlerBuilder builder() {
        return new DocHandlerBuilder();
    }

    /**
     * 初始化.
     */
    public void init() {
        PluginMasterConfigBean config = context.getBean(PluginMasterConfigBean.class);
        String value = config.getBasePackage();
        if (StringUtil.isEmpty(value)) {
            return;
        }
        if (HexagonDocContext.getSpi() == null) {
            return;
        }
        List<ExtDocInterface> extDocList = HexagonDocContext.getSpi().getExtDocList(value);
        log.info("extDocList {}", extDocList);
    }

    /**
     * 构造器Builder.
     */
    public static final class DocHandlerBuilder {
        private ConfigurableApplicationContext context;

        private DocHandlerBuilder() {
        }

        /**
         * 当前Spring容器上下文.
         *
         * @param context 容器上下文
         * @return this
         */
        public DocHandlerBuilder context(ConfigurableApplicationContext context) {
            this.context = context;
            return this;
        }

        /**
         * 生成DocHandler.
         *
         * @return DocHandler
         */
        public DocHandler build() {
            return new DocHandler(context);
        }
    }
}
