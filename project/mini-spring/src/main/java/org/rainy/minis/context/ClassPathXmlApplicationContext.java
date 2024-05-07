package org.rainy.minis.context;

import org.rainy.minis.beans.config.SingletonBeanRegistry;
import org.rainy.minis.beans.factory.BeanFactory;
import org.rainy.minis.beans.factory.DefaultListableBeanFactory;
import org.rainy.minis.beans.support.AutowiredAnnotationBeanPostProcessor;
import org.rainy.minis.beans.support.BeanDefinitionRegistry;
import org.rainy.minis.beans.support.DefaultBeanDefinitionRegistry;
import org.rainy.minis.beans.support.DefaultSingletonBeanRegistry;
import org.rainy.minis.beans.xml.BeanDefinitionReader;
import org.rainy.minis.core.resource.ClassPathXmlResource;
import org.rainy.minis.core.resource.Resource;
import org.rainy.minis.event.ApplicationEvent;
import org.rainy.minis.event.ContextRefreshEvent;
import org.rainy.minis.event.listener.ApplicationListener;
import org.rainy.minis.event.publisher.SimpleApplicationEventPublisher;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    private final DefaultListableBeanFactory beanFactory;

    public ClassPathXmlApplicationContext(String xmlPath) {
        this(xmlPath, true);
    }

    public ClassPathXmlApplicationContext(String xmlPath, boolean isRefresh) {
        Resource resource = new ClassPathXmlResource(xmlPath);
        SingletonBeanRegistry singletonBeanRegistry = new DefaultSingletonBeanRegistry();
        BeanDefinitionRegistry beanDefinitionRegistry = new DefaultBeanDefinitionRegistry();
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory(singletonBeanRegistry, beanDefinitionRegistry);
        BeanDefinitionReader beanDefinitionReader = new BeanDefinitionReader(beanDefinitionRegistry);
        beanDefinitionReader.loadBeanDefinition(resource);
        this.beanFactory = beanFactory;
        this.registerBeanPostProcessor();
        if (isRefresh) {
            refresh();
        }
    }

    public void registerBeanPostProcessor() {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor(this.beanFactory));
    }

    @Override
    protected void postProcessBeanFactory(BeanFactory beanFactory) {
    }

    @Override
    protected void registerBeanPostProcessor(BeanFactory beanFactory) {
        this.beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor(this.beanFactory));
    }

    @Override
    protected void initApplicationEventPublisher() {
        this.eventPublisher = new SimpleApplicationEventPublisher();
    }

    @Override
    protected void onRefresh() {
        this.beanFactory.refresh();
    }

    @Override
    protected void registerListener() {
        this.eventPublisher.addApplicationListener(new ApplicationListener());
    }

    @Override
    protected void finishRefresh() {
        this.publishEvent(new ContextRefreshEvent("context refreshed"));
    }


    @Override
    public BeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public String[] getBeanNames() {
        return this.beanFactory.getBeanNames();
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        this.eventPublisher.publishEvent(event);
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.eventPublisher.addApplicationListener(listener);
    }

}
