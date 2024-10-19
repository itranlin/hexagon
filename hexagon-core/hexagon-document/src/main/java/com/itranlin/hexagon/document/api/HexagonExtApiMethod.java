package com.itranlin.hexagon.document.api;

import java.lang.annotation.*;

/**
 * 用于描述 扩展点接口的方法
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HexagonExtApiMethod {

    /**
     * Description string.
     *
     * @return Description
     */
    String description();

    /**
     * Create time string.
     *
     * @return Create time
     */
    String createTime();
}
