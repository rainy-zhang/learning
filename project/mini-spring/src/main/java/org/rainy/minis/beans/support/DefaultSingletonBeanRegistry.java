package org.rainy.minis.beans.support;

import org.rainy.minis.beans.config.SingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected final Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

    @Override
    public void registerSingletonBean(String beanName, Object bean) {
        this.singletonObjects.put(beanName, bean);
    }

    @Override
    public Object getSingletonBean(String beanName) {
        return this.singletonObjects.get(beanName);
    }


    @Override
    public boolean containsSingletonBean(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }


    @Override
    public void removeSingletonBean(String beanName) {
        this.singletonObjects.remove(beanName);
    }

}
