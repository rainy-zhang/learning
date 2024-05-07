package org.rainy.minis.beans.config;

import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Data
public class BeanDefinition {

    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";

    private String scope;
    private String[] dependsOn;
    private boolean lazyInit;
    private String initMethod;
    private Object bean;
    private ConstructorArgumentValues constructorArgumentValues;
    private PropertyArgumentValues propertyArgumentValues;
    private final String id;
    private final String className;

}
