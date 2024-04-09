package org.rainy.minis.registry;

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
    protected final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();

    @Override
    public void registerSingletonBean(String beanName, Object bean) {
        this.singletonObjects.put(beanName, bean);
    }

    @Override
    public Object getSingletonBean(String beanName) {
        return this.singletonObjects.get(beanName);
    }

    @Override
    public Object getEarlySingletonBean(String beanName) {
        return this.earlySingletonObjects.get(beanName);
    }

    @Override
    public void registerEarlySingletonBean(String beanName, Object bean) {
        this.earlySingletonObjects.put(beanName, bean);
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
