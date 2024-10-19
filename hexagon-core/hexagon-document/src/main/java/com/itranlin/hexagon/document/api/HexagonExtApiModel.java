package com.itranlin.hexagon.document.api;

import java.lang.annotation.*;

/**
 * 实体类的描述
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HexagonExtApiModel {

    /**
     * Description string.
     *
     * @return Description
     */
    String description();
}
