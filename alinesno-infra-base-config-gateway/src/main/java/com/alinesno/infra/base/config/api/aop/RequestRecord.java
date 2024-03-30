package com.alinesno.infra.base.config.api.aop;

import java.lang.annotation.*;

/**
 * RequestRecord注解，用于标记需要记录请求日志的接口方法
 *
 * @author luoxiaodong
 * @version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestRecord {

    // 可以添加一些注解参数，如日志级别、日志内容格式等

    String desc() default "" ;
}
