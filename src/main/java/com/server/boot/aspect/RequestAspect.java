package com.server.boot.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RequestAspect {
// 왜 @Around(RestController 포인트컷) 은 적용이안되고(반응이 없음), Controller 포인트컷은 api요청은 이상하고, 콘솔은 찍히는지..
    @After("execution(* com.server.boot.controller.*.*(..))")
    public void test() throws Throwable {

        System.out.println("method execute");
        //Object obj = proceedingJoinPoint.proceed();
    }
}
