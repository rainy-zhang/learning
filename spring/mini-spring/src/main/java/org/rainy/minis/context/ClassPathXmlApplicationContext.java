package org.rainy.minis.context;

import org.rainy.minis.beans.factory.BeanFactory;
import org.rainy.minis.beans.factory.support.SimpleBeanFactory;
import org.rainy.minis.beans.factory.xml.XmlBeanDefinitionReader;
import org.rainy.minis.event.ApplicationEvent;
import org.rainy.minis.event.ApplicationEventPublisher;
import org.rainy.minis.exception.BeanException;
import org.rainy.minis.registry.DefaultSingletonBeanRegistry;
import org.rainy.minis.registry.SingletonBeanRegistry;
import org.rainy.minis.resource.ClassPathXmlResource;
import org.rainy.minis.resource.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class ClassPathXmlApplicationContext  implements ApplicationEventPublisher {

    private final BeanFactory beanFactory;

    @Override
    public void publishEvent(ApplicationEvent event) {

    }


    public ClassPathXmlApplicationContext(String fileName) {
        this(fileName, true);
    }

    public ClassPathXmlApplicationContext(String fileName, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(fileName);
        SingletonBeanRegistry singletonBeanRegistry = new DefaultSingletonBeanRegistry();
        BeanFactory beanFactory = new SimpleBeanFactory(singletonBeanRegistry);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(resource);
        this.beanFactory = beanFactory;
        if (isRefresh) {
            beanFactory.refresh();
        }
    }

    public Object getBean(String beanName) throws BeanException {
        return this.beanFactory.getBean(beanName);
    }

}
