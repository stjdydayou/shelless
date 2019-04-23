package com.dliyun.platform.common.freemarker;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author jtoms.shen
 * @version 1.0
 * @date 2018/12/6 15:55
 */
@Component
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FreemarkerComponent {
    String value() default "";
}
