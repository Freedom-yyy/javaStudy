package io.github.xxyopen.novel.core.annotation;

import io.github.xxyopen.novel.core.common.constant.ErrorCodeEnum;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyLock {

    String prefix() default ""; // 锁的名称

    boolean isWait() default false;//是否需要等待

    long waitTime() default 0; //等待时间

    ErrorCodeEnum failCode() default ErrorCodeEnum.USER_REQUEST_TOO_MANY;

}
