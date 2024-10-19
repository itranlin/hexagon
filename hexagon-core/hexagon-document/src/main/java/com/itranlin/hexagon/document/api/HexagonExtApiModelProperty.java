package com.itranlin.hexagon.document.api;

import java.lang.annotation.*;

/**
 * 实体类的属性
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HexagonExtApiModelProperty {

    /**
     * Description string.
     *
     * @return Description
     */
    String description();

}
