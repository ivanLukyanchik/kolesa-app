package by.kolesa.backend.tools.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LogExecutionTimeAspect {

  private static final Logger log = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

  @Pointcut("@annotation(by.kolesa.backend.tools.logging.LogExecutionTime)")
  public void logExecutionTimePointCut() {
    // point cut declaration within empty method
  }

  /**
   * This method uses Around advice which ensures that an advice can run before and after the method
   * execution, to and log the execution time of the method This advice will be be applied to all
   * the method which are annotate with the annotation @LogExecutionTime @see
   * com.example.springaop.logging.LogExecutionTime
   *
   * <p>Any mehtod where execution times need to be measure and log, annotate the method
   * with @LogExecutionTime example @LogExecutionTime public void m1();
   *
   * @param proceedingJoinPoint
   * @return
   * @throws Throwable
   */
  @Around("logExecutionTimePointCut()")
  public Object methodTimeLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

    // Get intercepted method details
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    // Measure method execution time
    StopWatch stopWatch = new StopWatch(className + "->" + methodName);
    stopWatch.start(methodName);
    Object result = proceedingJoinPoint.proceed();
    stopWatch.stop();
    // Log method execution time
    log.info(printShortSummary(stopWatch));
    return result;
  }

  private String printShortSummary(StopWatch stopWatch) {
    return new StringBuilder()
        .append("StopWatch '")
        .append(stopWatch.getId())
        .append("': running time = ")
        .append(stopWatch.getTotalTimeMillis())
        .append(" ms")
        .toString();
  }
}
