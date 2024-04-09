package org.rainy.minis.beans.factory;

import org.rainy.minis.beans.config.BeanDefinition;
import org.rainy.minis.exception.BeanException;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeanException;

    void registerBean(String beanName, Object bean);

    boolean containsBean(String beanName);

    boolean isSingleton(String beanName);

    boolean isPrototype(String beanName);

    Class<?> getType(String beanName);

    void refresh();

    void registerBeanDefinition(BeanDefinition beanDefinition);

    List<BeanDefinition> getBeanDefinitions();

}
