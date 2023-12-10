package com.ms.datawise.distn.annotation;

import java.lang.annotation. Retention; 
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target ({FIELD, METHOD})
@Retention (RUNTIME)
public @interface RetryMethod {
	**
	* Retry count. default value 3
	*/
	public int retryAttempts () default 3;
	public long sleepInterval() default 10000L; //milliseconds
	Class<? extends Throwable> [] retryExceptions () default { RuntimeException.class };
}