package org.rainy.minis.beans.factory.support;

import lombok.extern.slf4j.Slf4j;
import org.rainy.minis.beans.BeanDefinitionRegistry;
import org.rainy.minis.beans.config.BeanDefinition;
import org.rainy.minis.beans.config.ConstructorArguments;
import org.rainy.minis.beans.config.PropertyArguments;
import org.rainy.minis.beans.factory.BeanFactory;
import org.rainy.minis.exception.BeanException;
import org.rainy.minis.registry.SingletonBeanRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
@Slf4j
public class SimpleBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitions = new HashMap<>();
    private final SingletonBeanRegistry singletonBeanRegistry;
    private final List<String> beanDefinitionNames = new ArrayList<>();

    public SimpleBeanFactory(SingletonBeanRegistry singletonBeanRegistry) {
        this.singletonBeanRegistry = singletonBeanRegistry;
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
        // 尝试从 DefaultSingletonBeanRegistry 中获取bean的实例
        Object bean = this.singletonBeanRegistry.getSingletonBean(beanName);
        // 如果获取不到说明bean还没有初始化
        if (bean == null) {
            // 再从earlySingletonObjects获取毛坯实例
            bean = this.singletonBeanRegistry.getEarlySingletonBean(beanName);

            // 获取不到则创建bean
            if (bean == null) {
                BeanDefinition beanDefinition = beanDefinitions.get(beanName);
                if (beanDefinition == null) {
                    throw new BeanException("beanDefinition is null: " + beanName);
                }
                bean = this.createBean(beanDefinition);
                this.registerBean(beanName, bean);
            }

        }
        return bean;
    }

    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(beanDefinition.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object bean = this.doCreateBean(clazz, beanDefinition);
        this.singletonBeanRegistry.registerEarlySingletonBean(beanDefinition.getId(), bean);

        this.handleProperties(beanDefinition, clazz, bean);
        return bean;
    }


    //doCreateBean方法只创建"毛胚"实例，仅仅调用构造方法，不进行属性处理
    private Object doCreateBean(Class<?> clazz, BeanDefinition beanDefinition) {
        Object bean = null;
        try {
            ConstructorArguments constructorArguments = beanDefinition.getConstructorArguments();
            if (!constructorArguments.isEmpty()) {
                List<ConstructorArguments.ConstructorValue> constructorValues = constructorArguments.getConstructValues();
                Class<?>[] parameterTypes = new Class<?>[constructorValues.size()];
                Object[] parameterValues = new Object[constructorValues.size()];
                for (ConstructorArguments.ConstructorValue constructorValue : constructorValues) {
                    String type = constructorValue.getType();
                    if (!constructorValue.isRef()) {
                        if ("String".equals(type) || "java.lang.String".equals(type)) {
                            parameterTypes[0] = String.class;
                        } else if ("Integer".equals(type) || "java.lang.Integer".equals(type) || "int".equals(type)) {
                            parameterTypes[0] = Integer.class;
                        } else {
                            parameterTypes[0] = String.class;
                        }
                        parameterValues[0] = constructorValue.getValue();
                    } else {
                        parameterTypes[0] = Class.forName(type);
                        try {
                            parameterValues[0] = getBean(constructorValue.getValue().toString());
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
        PropertyArguments propertyArguments = beanDefinition.getPropertyArguments();
        // 处理属性
        if (!propertyArguments.isEmpty()) {
            List<PropertyArguments.PropertyValue> propertyValues = propertyArguments.getPropertyValues();
            Class<?>[] parameterTypes = new Class<?>[propertyValues.size()];
            Object[] parameterValues = new Object[propertyValues.size()];
            for (PropertyArguments.PropertyValue propertyValue : propertyValues) {
                String type = propertyValue.getType();
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (!propertyValue.isRef()) {
                    if ("String".equals(type) || "java.lang.String".equals(type)) {
                        parameterTypes[0] = String.class;
                    } else if ("Integer".equals(type) || "java.lang.Integer".equals(type) || "int".equals(type)) {
                        parameterTypes[0] = Integer.class;
                    } else {
                        parameterTypes[0] = String.class;
                    }
                    parameterValues[0] = propertyValue.getValue();
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

    @Override
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        String beanName = beanDefinition.getId();
        this.beanDefinitions.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        return new ArrayList<>(this.beanDefinitions.values());
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return this.beanDefinitions.containsKey(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        return this.beanDefinitions.get(beanName);
    }

    @Override
    public void removeBeanDefinition(String beanName) {
        this.beanDefinitions.remove(beanName);
        this.singletonBeanRegistry.removeSingletonBean(beanName);
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
        return this.getBeanDefinition(beanName).getScope().equals(BeanDefinition.SCOPE_SINGLETON);
    }

    @Override
    public boolean isPrototype(String beanName) {
        return this.getBeanDefinition(beanName).getScope().equals(BeanDefinition.SCOPE_PROTOTYPE);
    }

    @Override
    public Class<?> getType(String beanName) {
        return this.getBeanDefinition(beanName).getBeanClass();
    }

    @Override
    public void refresh() {
        for (String beanName : this.beanDefinitionNames) {
            try {
                BeanDefinition beanDefinition = getBeanDefinition(beanName);
                if (beanDefinition.isLazyInit()) {
                    continue;
                }
                System.out.println(getBean(beanName));
            } catch (BeanException e) {
                e.printStackTrace();
            }
        }
    }

}
