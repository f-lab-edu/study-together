package dev.flab.studytogether.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAop {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    /*
    Post 요청 로깅
     */
    @Pointcut("@annotation(dev.flab.studytogether.aop.PostMethodLog)")
    public void postMappingLog(){}

    @Before("postMappingLog()")
    public void logBeforePostMapping(JoinPoint joinPoint){
        log.info("==================== Before Post Request ====================");
        Object[] args = joinPoint.getArgs();
        if (args.length <= 0) log.info("no parameter");
        for (Object arg : args) {
            log.info("parameter type : {}, parameter value : {}", arg.getClass().getSimpleName(), arg);
        }

    }

    @AfterReturning(value = "postMappingLog()", returning = "returnObj")
    public void logAfterPostMapping(JoinPoint joinPoint, Object returnObj){
        log.info("==================== After Post Request ====================");
        log.info("return type : {}, return value : {}", returnObj.getClass().getSimpleName(), returnObj);
    }

    /*
    Get 요청 로깅
     */
    @Pointcut("@annotation(dev.flab.studytogether.aop.GetMethodLog)")
    public void getMappingLog(){}

    @Before("getMappingLog()")
    public void logBeforeGetMapping(JoinPoint joinPoint){
        log.info("==================== Before Get Request ====================");
        Object[] args = joinPoint.getArgs();
        if (args.length <= 0) log.info("no parameter");
        for (Object arg : args) {
            log.info("parameter type : {}, parameter value : {}", arg.getClass().getSimpleName(), arg);
        }

    }

    @AfterReturning(value = "getMappingLog()", returning = "returnObj")
    public void logAfterGetMapping(JoinPoint joinPoint, Object returnObj){
        log.info("==================== After Get Request ====================");
        log.info("return type : {}, return value : {}", returnObj.getClass().getSimpleName(), returnObj);
    }

}
