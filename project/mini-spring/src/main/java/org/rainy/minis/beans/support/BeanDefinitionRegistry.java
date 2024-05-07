package org.rainy.minis.beans.support;

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

    BeanDefinition getBeanDefinition(String beanName);

    void removeBeanDefinition(String beanName);

    String[] getBeanDefinitionNames();

    void registerBeanDefinitionByPackage(String packageName);


}
