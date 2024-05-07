package org.rainy.minis.beans.factory;

import org.rainy.minis.exception.BeanException;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface BeanFactory {

    Object getBean(String beanName) throws BeanException;

    String[] getBeanNames();

    void registerBean(String beanName, Object bean);

    boolean containsBean(String beanName);

    boolean isSingleton(String beanName);

    boolean isPrototype(String beanName);

    Class<?> getType(String beanName);

    void refresh();

}
