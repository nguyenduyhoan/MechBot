package hoannd.mech.bot.MechBot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Áp dụng cho tất cả các method trong package `service`
    @Pointcut("execution(* hoannd.mech.bot.MechBot.service..*(..))")
    public void serviceMethods() {}

    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Start Method: " + joinPoint.getSignature().getName() +
                ", Args: " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        logger.info("End Method: " + joinPoint.getSignature().getName() +
                ", Result: " + result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "e")
    public void logException(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in Method: " + joinPoint.getSignature().getName(), e);
    }
}

