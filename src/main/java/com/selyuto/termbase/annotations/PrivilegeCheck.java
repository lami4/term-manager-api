package com.selyuto.termbase.annotations;

import com.selyuto.termbase.enums.Privilege;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PrivilegeCheck {
    Privilege[] privileges() default {};
}
