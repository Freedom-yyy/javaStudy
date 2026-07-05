package io.github.xxyopen.novel.core.aspect;

import io.github.xxyopen.novel.core.annotation.MyKey;
import io.github.xxyopen.novel.core.annotation.MyLock;
import io.github.xxyopen.novel.core.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class MyLockAspect {
    private final RedissonClient redissonClient;

    @Around(value = "@annotation(io.github.xxyopen.novel.core.annotation.MyLock)")
    @SneakyThrows
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获得加锁的方法的方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取对应的方法
        Method method = methodSignature.getMethod();
        //获取锁的信息
        MyLock lock = method.getAnnotation(MyLock.class);
        //拼接redis锁的key
        String myLockKey = getKey(method, joinPoint, lock);
        RLock rLock = redissonClient.getLock(myLockKey);
        boolean isLock;
        //是否支持等待锁
        if (lock.isWait()) {
            isLock = rLock.tryLock(lock.waitTime(), TimeUnit.SECONDS);
        } else {
            isLock = rLock.tryLock();
        }

        if (!isLock) {
            throw new BusinessException(lock.failCode());
        }
        try {
            return joinPoint.proceed();
        } finally {
            rLock.unlock();
        }

    }

    private String getKey(Method method, ProceedingJoinPoint joinPoint, MyLock myLock) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        String key = "";
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof MyKey myKey) {
                    String expr = myKey.expr();
                    if (expr == null || expr.length() == 0) {
                        key = args[i].toString();
                    } else {
                        key = parseKeyExpr(args[i], expr);//如果参数是DTO，那么拿到的就不是linkurl，而是整个对象的字符串
                    }

                }
            }

        }
        key = "Lock::" + myLock.prefix() + "::" + key;
        return key;
    }

    private String parseKeyExpr(Object arg, String expr) {
        if (!StringUtils.hasText(expr)) {
            return arg.toString();
        }
        ExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression(expr, new TemplateParserContext());
        return exp.getValue(arg, String.class);

    }

}
