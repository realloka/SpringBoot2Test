package com.plantynet.tech2.support.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic="SAMPLE")
@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class LoggingAspect
{
    //포인트 컷을 아래처럼 메소드로 만들어서 지정할 수 있음
    @Pointcut("execution(* com.plantynet.tech2.service.impl.TestServiceImpl.*(..))")
    public void testServiceImpl() {}
    
    //@Before("execution(* com.plantynet.tech2.service.impl.TestServiceImpl.*(..))")
    @Before("testServiceImpl()")
    public void logBefore(JoinPoint joinPoint) 
    {
        String className = joinPoint.getTarget().getClass().getName();
        Object[] objs = joinPoint.getArgs();
        
        String param = "";
        
        log.info("=========================logBefore()=========================");
        log.info(String.format("[class-method] : %s.%s", className, joinPoint.getSignature().getName()));
        for(Object item:objs)
        {
            if(item != null)
            {
                param += item.toString() + "\n";
            }
        }
        log.info("[parameter] : " + param);
    }
    
    //@AfterReturning(pointcut="execution(* com.plantynet.tech2.service.impl.TestServiceImpl.*(..))", returning="ret")
    @AfterReturning(pointcut="testServiceImpl()", returning="ret")
    public void afterReturning(JoinPoint joinPoint, Object ret)
    {
        String className = joinPoint.getTarget().getClass().getName();
        
        log.info("=========================logAfterReturning()=========================");
        log.info(String.format("[class-method] : %s.%s", className, joinPoint.getSignature().getName()));
        if(ret != null)
        {
            log.info("[return] : " + ret.toString());
        }
    }
    
    //http://victorydntmd.tistory.com/178 수행 시간
    //testServiceImpl에 있는 메서드
    /*@Around("execution(* com.plantynet.tech2.service.impl.TestServiceImpl.*(..))")
    public Object aroundAdvice( ProceedingJoinPoint pjp) throws Throwable 
    {
        // before advice
        StopWatch sw = new StopWatch();
        sw.start();
        
        Object result = pjp.proceed();
        
        // after advice
        sw.stop();
        Long total = sw.getTotalTimeMillis();
        
        // 어떤 클래스의 메서드인지 출력하는 정보는 pjp 객체에 있다.
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String taskName = className + "." + methodName;
        
        // 실행시간은 로그로 남기는 것이 좋지만, 여기서는 콘솔창에 찍도록 한다.
        apiLogger.info("[ExecutionTime] " + taskName + " , " + total + "(ms)");
        
        return result;
    }*/
}