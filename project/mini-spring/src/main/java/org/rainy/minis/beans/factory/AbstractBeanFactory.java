package org.rainy.minis.beans.factory;

import org.apache.commons.lang3.StringUtils;
import org.rainy.minis.beans.config.BeanDefinition;
import org.rainy.minis.beans.config.ConstructorArgumentValues;
import org.rainy.minis.beans.config.PropertyArgumentValues;
import org.rainy.minis.beans.config.SingletonBeanRegistry;
import org.rainy.minis.beans.support.BeanDefinitionRegistry;
import org.rainy.minis.exception.BeanException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    protected final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>();
    protected final SingletonBeanRegistry singletonBeanRegistry;
    protected final BeanDefinitionRegistry beanDefinitionRegistry;

    protected AbstractBeanFactory(SingletonBeanRegistry singletonBeanRegistry, BeanDefinitionRegistry beanDefinitionRegistry) {
        this.singletonBeanRegistry = singletonBeanRegistry;
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
        // 尝试从 DefaultSingletonBeanRegistry 中获取bean的实例
        Object bean = this.singletonBeanRegistry.getSingletonBean(beanName);
        // 如果获取不到说明bean还没有初始化
        if (bean == null) {
            // 再从earlySingletonObjects获取毛坯实例
            bean = this.earlySingletonObjects.get(beanName);

            // 获取不到则创建bean
            if (bean == null) {
                BeanDefinition beanDefinition = this.beanDefinitionRegistry.getBeanDefinition(beanName);
                if (beanDefinition == null) {
                    throw new BeanException("beanDefinition is null: " + beanName);
                }
                bean = this.createBean(beanDefinition);
                beanDefinition.setBean(bean);
                this.registerBean(beanName, bean);

                this.applyBeanPostProcessorsBeforeInitialization(beanName, bean);
                invokeInitMethod(beanDefinition);
                this.applyBeanPostProcessorsAfterInitialization(beanName, bean);
            }

        }
        return bean;
    }

    @Override
    public void registerBean(String beanName, Object bean) {
        this.singletonBeanRegistry.registerSingletonBean(beanName, bean);
    }

    @Override
    public boolean containsBean(String beanName) {
        return this.singletonBeanRegistry.containsSingletonBean(beanName);
    }

    @Override
    public boolean isSingleton(String beanName) {
        return this.beanDefinitionRegistry.getBeanDefinition(beanName).getScope().equals(BeanDefinition.SCOPE_SINGLETON);
    }

    @Override
    public boolean isPrototype(String beanName) {
        return this.beanDefinitionRegistry.getBeanDefinition(beanName).getScope().equals(BeanDefinition.SCOPE_PROTOTYPE);
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.beanDefinitionRegistry.getBeanDefinition(beanName).getBean().getClass();
    }

    @Override
    public void refresh() {
        for (String beanName : this.beanDefinitionRegistry.getBeanDefinitionNames()) {
            try {
                BeanDefinition beanDefinition = this.beanDefinitionRegistry.getBeanDefinition(beanName);
                if (beanDefinition.isLazyInit()) {
                    continue;
                }
                System.out.println(getBean(beanName));
            } catch (BeanException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String[] getBeanNames() {
        return this.beanDefinitionRegistry.getBeanDefinitionNames();
    }

    protected void applyBeanPostProcessorsBeforeInitialization(String beanName, Object bean) {
    }

    protected void applyBeanPostProcessorsAfterInitialization(String beanName, Object bean) {
    }

    private void invokeInitMethod(BeanDefinition beanDefinition) {
        String initMethod = beanDefinition.getInitMethod();
        if (StringUtils.isEmpty(initMethod)) {
            return;
        }

        Object bean = beanDefinition.getBean();
        try {
            Method method = bean.getClass().getMethod(initMethod);
            method.invoke(bean);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object bean = this.doCreateBean(clazz, beanDefinition);
        this.earlySingletonObjects.put(beanDefinition.getId(), bean);

        this.handleProperties(beanDefinition, clazz, bean);
        return bean;
    }


    //doCreateBean方法只创建"毛胚"实例，仅仅调用构造方法，不进行属性处理
    private Object doCreateBean(Class<?> clazz, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            ConstructorArgumentValues constructorArgumentValues = beanDefinition.getConstructorArgumentValues();
            if (!constructorArgumentValues.isEmpty()) {
                Class<?>[] parameterTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                Object[] parameterValues = new Object[constructorArgumentValues.getArgumentCount()];
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    ConstructorArgumentValues.ConstructorArgumentValue constructorArgument = constructorArgumentValues.getConstructorValue(i);
                    String type = constructorArgument.getType();
                    if (!constructorArgument.isRef()) {
                        if ("String".equals(type) || "java.lang.String".equals(type)) {
                            parameterTypes[0] = String.class;
                        } else if ("Integer".equals(type) || "java.lang.Integer".equals(type) || "int".equals(type)) {
                            parameterTypes[0] = Integer.class;
                        } else {
                            parameterTypes[0] = String.class;
                        }
                        parameterValues[0] = constructorArgument.getValue();
                    } else {
                        parameterTypes[0] = Class.forName(type);
                        try {
                            parameterValues[0] = getBean(constructorArgument.getValue().toString());
                        } catch (BeanException e) {
                            e.printStackTrace();
                        }
                    }
                }

                Constructor<?> constructor = clazz.getConstructor(parameterTypes);
                bean = constructor.newInstance(parameterValues);
            } else {
                bean = clazz.newInstance();
            }

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return bean;
    }

    private void handleProperties(BeanDefinition beanDefinition, Class<?> clazz, Object bean) {
        PropertyArgumentValues propertyArgumentValues = beanDefinition.getPropertyArgumentValues();
        // 处理属性
        if (!propertyArgumentValues.isEmpty()) {
            Class<?>[] parameterTypes = new Class<?>[propertyArgumentValues.getPropertyValueCount()];
            Object[] parameterValues = new Object[propertyArgumentValues.getPropertyValueCount()];
            for (int i = 0; i < propertyArgumentValues.getPropertyValueCount(); i++) {
                PropertyArgumentValues.PropertyValue property = propertyArgumentValues.getProperty(i);
                String type = property.getType();
                String name = property.getName();
                Object value = property.getValue();
                if (!property.isRef()) {
                    if ("String".equals(type) || "java.lang.String".equals(type)) {
                        parameterTypes[0] = String.class;
                    } else if ("Integer".equals(type) || "java.lang.Integer".equals(type) || "int".equals(type)) {
                        parameterTypes[0] = Integer.class;
                    } else if ("java.util.Map".equals(type)) {
                        parameterTypes[0] = java.util.Map.class;
                    }else {
                        parameterTypes[0] = String.class;
                    }
                    parameterValues[0] = property.getValue();
                } else {
                    try {
                        parameterTypes[0] = Class.forName(type);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        parameterValues[0] = getBean(value.toString());
                    } catch (BeanException e) {
                        e.printStackTrace();
                    }
                }

                // 调用set方法对属性赋值
                String methodName = String.format("set%s%s", name.substring(0, 1).toUpperCase(), name.substring(1));
                try {
                    Method method = clazz.getMethod(methodName, parameterTypes);
                    method.invoke(bean, parameterValues);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
