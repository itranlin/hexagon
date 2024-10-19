package com.itranlin.hexagon.document.api;

import java.lang.annotation.*;

/**
 * 用于描述 扩展点接口.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HexagonExtApiInterface {

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
