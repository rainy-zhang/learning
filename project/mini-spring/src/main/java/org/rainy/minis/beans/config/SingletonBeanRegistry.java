package org.rainy.minis.beans.config;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface SingletonBeanRegistry {

    void registerSingletonBean(String beanName, Object bean);

    Object getSingletonBean(String beanName);

    boolean containsSingletonBean(String beanName);

    void removeSingletonBean(String beanName);


}
