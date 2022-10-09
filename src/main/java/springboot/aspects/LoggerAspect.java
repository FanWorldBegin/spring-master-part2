package springboot.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Around("execution(* springboot..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取method 名字
        log.info(joinPoint.getSignature().toString() + " method execution start");
        // 获取当前时间
        Instant start = Instant.now();
        // 触发正常的逻辑正常执行
        Object returnObj = joinPoint.proceed();
        Instant finish = Instant.now();
        // 获取执行时间
        long timeElapsed = Duration.between(start, finish).toMillis();
        // 执行可数
        log.info("Time took to execute " + joinPoint.getSignature().toString() + " method is : "+timeElapsed);
        log.info(joinPoint.getSignature().toString() + " method execution end");
        return returnObj;
    }

    // actual method is throwing an exception 当有异常抛出
    @AfterThrowing(value = "execution(* springboot.*.*(..))",throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error(joinPoint.getSignature()+ " An exception happened due to : "+ex.getMessage());
    }


}
