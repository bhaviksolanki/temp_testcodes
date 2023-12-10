package com.ms.datawise.distn.aop;
import com.ms.datawise.distn.annotation.Retry; 
import org.aspectj.lang. ProceedingJoinPoint; 
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import java.util.HashSet;
import java.util.Set;

@Aspect
public class IngestionRetryAspect implements Ordered {
	
	private static Logger LOGGER = LoggerFactory.getLogger(IngestionRetryAspect.class); 
	private int order = -1;
	
// @Around (value = "@annotation (ingestionRetry)", argNames = "ingestionRetry")
	public Object concurrencyRetry (final ProceedingJoinPoint pjp,
			final Retry retry) throws Throwable {
		final Integer maxRetries
		Integer retryAttempt = 0;
		Object result = null;
		retry.retryAttempts();
		
		Class<? extends Throwable> [] retryExceptions = retry.retryExceptions(); 
		while (retryAttempt < maxRetries) {
			try {
					result pjp.proceed(); break;
				} catch (final Exception exception) { 
					Thread.sleep(retry.sleepInterval());
					retryAttempt = handleException (exception, retryAttempt, maxRetries, retryExceptions);
				}
			}
		return result;
	}
	
	private Integer handleException (final Exception exception, Integer retryAttempt,
		final Integer maxRetries, Class<? extends Throwable>... retryExceptions) throws Exception { 
		
		if (isIngestionException (exception, retryExceptions)) {
			LOGGER.error("Exception from Ingestion ", exception.getMessage());
			
			if (retryAttempt == maxRetries) {
				throw exception;
			}
			retryAttempt++;
		} else {
			throw exception;
		}
		
		return retryAttempt;
		}
		
		
//This method is to check if the Exception thrown by code is what method has to retry on
	private Boolean is IngestionException (final Exception thrownException, Class<? extends Throwable>... retryExceptions) { 
		Set<Class<? extends Throwable>> retryExceptionsSet = new HashSet<Class<? extends Throwable>>();
		if (retryExceptions != null && retryExceptions.length > 0) {
			for (Class<? extends Throwable> retryException: retryExceptions) {
				retryExceptionsSet.add(retryException);
			}
		}
		
		LOGGER.error(" Retry to be performed for exceptions: " + retryExceptionsSet);
		
		for (Class<? extends Throwable> retryExceptionClazz: retryExceptionsSet) {
			LOGGER.error(" Checking if thrown exception is: " + retryExceptionClazz.getName());
			
			if (retryExceptionClazz.isAssignable From (thrownException.getClass())) {
				LOGGER.error("Thrown exception {} matches is part of retry Exception list", thrownException.getClass()); 
				return true;
			}
		}
		return false;
	}
	
	
	public int getOrder() {
		return order;
	}
	
	public void setOrder (final int order) {
		this.order = order;
	}
}