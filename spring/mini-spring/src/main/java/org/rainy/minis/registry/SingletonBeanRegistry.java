package org.rainy.minis.registry;

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

    Object getEarlySingletonBean(String beanName);

    void registerEarlySingletonBean(String beanName, Object bean);

    boolean containsSingletonBean(String beanName);

    void removeSingletonBean(String beanName);


}
