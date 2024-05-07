package org.rainy.minis.beans.factory;

import org.rainy.minis.beans.config.BeanDefinition;
import org.rainy.minis.beans.config.SingletonBeanRegistry;
import org.rainy.minis.beans.support.BeanDefinitionRegistry;
import org.rainy.minis.exception.BeanException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class DefaultListableBeanFactory extends AutowireCapableBeanFactory implements ConfigurableListableBeanFactory {

    public DefaultListableBeanFactory(SingletonBeanRegistry singletonBeanRegistry, BeanDefinitionRegistry beanDefinitionRegistry) {
        super(singletonBeanRegistry, beanDefinitionRegistry);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        List<String> beanNames = new ArrayList<>();
        for (String beanName : this.beanDefinitionRegistry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = this.beanDefinitionRegistry.getBeanDefinition(beanName);
            Class<?> matchClass = beanDefinition.getBean().getClass();
            if (type.isAssignableFrom(matchClass)) {
                beanNames.add(beanName);
            }
        }
        return beanNames.toArray(new String[0]);
    }

    @Override
    public <T> Map<String, T> getBeansForType(Class<T> type) throws BeanException {
        String[] beanNames = this.getBeanNamesForType(type);
        Map<String, T> beanDefinitionMap = new HashMap<>();
        for (String beanName : beanNames) {
            beanDefinitionMap.put(beanName, (T) this.getBean(beanName));
        }
        return beanDefinitionMap;
    }


}
