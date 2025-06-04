package com.backend.procuct_backend.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    /**
     *  pointcut that matches Web REST endpoints.
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void springBeanPointcut() {

    }
    @Pointcut("within(com.backend.procuct_backend.*)" +
            " || within(com.backend.procuct_backend.controller..*)")
    public void applicationPackagePointcut() {

    }
    /**
     *  Advice that logs methods throwing exception.
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with message = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),e.getMessage());
    }

    /**
     *  Advice that logs when a method is entered and existed.
     *
     * @param joinPoint join point for advice
     * @return  result
     * @throws  Throwable throws IllegalArgumentException
     */

    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.debug("Entrer: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();
        log.debug("Exist: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
        return result;
    }
}


