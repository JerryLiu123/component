package com.lgh.dsfes.helper.note;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Documented
@Retention(RetentionPolicy.RUNTIME)//此语句表示 此注解表示注解的信息被保留在class文件(字节码文件)中当程序编译时，会被虚拟机保留在运行时
@Target({ElementType.METHOD})//修饰的注解表示该注解只能用来修饰在方法上。
@Order(Ordered.HIGHEST_PRECEDENCE+1)
public @interface TargetDataSource {
	String value() default "";
}
