package com.ms.datawise.distn.annotation;

import java.lang.annotation. Retention; 
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD; 
import static java.lang.annotation.Retention Policy.RUNTIME;

@Target({ FIELD }) 
@Retention (RUNTIME)
public @interface RegexValidated {

}