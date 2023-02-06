package com.demo.client.service.aop;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Order(-1)
@Aspect
@Configuration
@Setter
public class DemoBeanAspect {

    @Pointcut("execution(* com.demo.client.service.TestAopService.*(..))")
    public void restCut() {
        //do nothing
    }

    @Around("restCut()")
    public Object restCutAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        long start = System.currentTimeMillis();

        //執行主程序
        Object retObj = joinPoint.proceed();

        log.info("path: {}, time: {}", attributes.getRequest().getServletPath(), (System.currentTimeMillis() - start));

        return retObj;
    }


}
