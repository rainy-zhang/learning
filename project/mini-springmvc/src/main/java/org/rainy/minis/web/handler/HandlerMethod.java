package org.rainy.minis.web.handler;

import lombok.Builder;
import lombok.Getter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Builder
@Getter
public class HandlerMethod {

    private Object bean;
    private Method method;
    private Parameter[] parameters;
    private Class<?> returnType;

}
