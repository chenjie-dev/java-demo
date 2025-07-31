package com.chenjie.javabase.Base.reflection;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // 拦截所有Service层方法
    @Around("execution(* com.example.service.*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        // 通过反射获取方法信息
        String className = method.getDeclaringClass().getSimpleName();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();

        System.out.printf("[AOP] 进入 %s.%s(), 参数: %s%n", className, methodName, Arrays.toString(args));

        try {
            Object result = joinPoint.proceed(); // 继续执行原方法
            System.out.printf("[AOP] 离开 %s.%s(), 结果: %s%n", className, methodName, result);
            return result;
        } catch (Exception e) {
            System.out.printf("[AOP] 方法 %s.%s() 抛出异常: %s%n", className, methodName, e.getMessage());
            throw e;
        }
    }
}