package org.rainy.minis.beans.support;

import org.rainy.minis.beans.annotation.Autowired;
import org.rainy.minis.beans.factory.BeanFactory;
import org.rainy.minis.exception.BeanException;

import java.lang.reflect.Field;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class AutowiredAnnotationBeanPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;

    public AutowiredAnnotationBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void postProcessorBeforeInitialization(String beanName, Object bean) throws BeanException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            try {
                field.setAccessible(true);
                String fieldName = field.getName();
                field.set(bean, this.beanFactory.getBean(fieldName));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void postProcessorAfterInitialization(String beanName, Object bean) {
    }

}
