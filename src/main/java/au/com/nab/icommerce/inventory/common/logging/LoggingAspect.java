package au.com.nab.icommerce.inventory.common.logging;

import au.com.nab.icommerce.inventory.proxy.event.source.AuditActivityPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger log = LoggerFactory.getLogger(LoggingAspect.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private final AuditActivityPublisher auditActivityPublisher;

    public LoggingAspect(AuditActivityPublisher auditActivityPublisher) {
        this.auditActivityPublisher = auditActivityPublisher;
    }


    @Pointcut("within(au.com.nab.icommerce.inventory..*)")
    public void inNABIcommerceInventoryPackage() {
    }

    /**
     * The pointcut for all rest controller methods
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice
     * @return result
     * @throws Throwable throws IllegalArgumentException
     */
    @Around("inNABIcommerceInventoryPackage()"
            + "&& restControllerMethods()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("Current method: {}()", joinPoint.getSignature().getName());

            log.info("File Path ( Class name ): {}", joinPoint.getSignature().getDeclaringTypeName());

            String remark = String.format("-----> Enter: %s.%s()", joinPoint.getSignature().getDeclaringTypeName(), "[" +  joinPoint.getSignature().getName() + "]") ;
            log.info("Remark: {}",remark);

            Object result = joinPoint.proceed();
            auditActivityPublisher.sendAuditActivity();
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName());
            throw e;
        }
    }
}
