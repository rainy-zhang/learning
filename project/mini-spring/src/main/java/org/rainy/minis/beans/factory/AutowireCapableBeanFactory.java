package org.rainy.minis.beans.factory;

import org.rainy.minis.beans.config.SingletonBeanRegistry;
import org.rainy.minis.beans.support.BeanDefinitionRegistry;
import org.rainy.minis.beans.support.BeanPostProcessor;
import org.rainy.minis.exception.BeanException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class AutowireCapableBeanFactory extends AbstractBeanFactory {

    protected final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    public AutowireCapableBeanFactory(SingletonBeanRegistry singletonBeanRegistry, BeanDefinitionRegistry beanDefinitionRegistry) {
        super(singletonBeanRegistry, beanDefinitionRegistry);
    }

    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.add(beanPostProcessor);
    }

    @Override
    protected void applyBeanPostProcessorsBeforeInitialization(String beanName, Object bean) {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            try {
                beanPostProcessor.postProcessorBeforeInitialization(beanName, bean);
            } catch (BeanException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void applyBeanPostProcessorsAfterInitialization(String beanName, Object bean) {
        for (BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            beanPostProcessor.postProcessorAfterInitialization(beanName, bean);
        }
    }

}
