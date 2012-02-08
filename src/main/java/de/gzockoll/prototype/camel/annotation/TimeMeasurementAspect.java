/**
 * Created 07.02.2012
 * This code is copyright (c) 2004 PAYONE Gmbh & Co. KG.
 */
package de.gzockoll.prototype.camel.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Guido Zockoll
 * 
 */
public class TimeMeasurementAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimeMeasurementAspect.class);

    @Around("tracePointcut() && @annotation(TimeMeasurement) && !within(TimeMeasurementAspect)")
    public Object profile(ProceedingJoinPoint pjp) throws Throwable {
        Object output;
        long start = System.currentTimeMillis();
        logger.debug("Going to call the method.");
        try {
            output = pjp.proceed();
        } finally {
            long elapsedTime = System.currentTimeMillis() - start;
            logger.debug("Method execution time: " + elapsedTime + " milliseconds.");
        }

        return output;
    }

}
