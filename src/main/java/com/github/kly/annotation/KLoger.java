package com.github.kly.annotation;

import java.lang.annotation.*;

/*
 * @author: Lingyu Kong
 * @description: 自定义注解接口类，@Target表明该注解生效的位置是在方法，@Rentention注解表明该注解接口生效周期是RUNTIME
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface KLoger {
}
