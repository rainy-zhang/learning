package org.rainy.minis.beans.factory;

import lombok.extern.slf4j.Slf4j;
import org.rainy.minis.beans.config.SingletonBeanRegistry;
import org.rainy.minis.beans.support.BeanDefinitionRegistry;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class SimpleBeanFactory extends AbstractBeanFactory {

    public SimpleBeanFactory(SingletonBeanRegistry singletonBeanRegistry, BeanDefinitionRegistry beanDefinitionRegistry) {
        super(singletonBeanRegistry, beanDefinitionRegistry);
    }

}
