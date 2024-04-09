package org.rainy.minis.beans;

import org.rainy.minis.beans.config.BeanDefinition;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(BeanDefinition beanDefinition);
    boolean containsBeanDefinition(String beanName);
    BeanDefinition getBeanDefinition(String beanName);
    void removeBeanDefinition(String beanName);

}
