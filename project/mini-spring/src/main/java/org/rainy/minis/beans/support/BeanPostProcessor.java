package org.rainy.minis.beans.support;

import org.rainy.minis.exception.BeanException;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface BeanPostProcessor {

    void postProcessorBeforeInitialization(String beanName, Object bean) throws BeanException;

    void postProcessorAfterInitialization(String beanName, Object bean);

}
