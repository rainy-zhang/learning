package org.rainy.minis.context;

import org.rainy.minis.beans.factory.BeanFactory;
import org.rainy.minis.beans.support.BeanFactoryPostProcessor;
import org.rainy.minis.core.env.Environment;
import org.rainy.minis.event.publisher.ApplicationEventPublisher;
import org.rainy.minis.exception.BeanException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    private final AtomicBoolean active = new AtomicBoolean(true);
    private long startupDate;
    private Environment environment;
    protected final List<BeanFactoryPostProcessor> beanFactoryPostProcessors = new ArrayList<>();
    protected ApplicationEventPublisher eventPublisher;


    @Override
    public void refresh() {
        this.postProcessBeanFactory(this.getBeanFactory());
        this.registerBeanPostProcessor(this.getBeanFactory());
        this.initApplicationEventPublisher();
        this.onRefresh();
        this.registerListener();
        this.finishRefresh();
    }

    @Override
    public void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor) {
        this.beanFactoryPostProcessors.add(postProcessor);
    }

    @Override
    public long getStartupDate() {
        return this.startupDate;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Environment getEnvironment() {
        return this.environment;
    }

    @Override
    public String getApplicationName() {
        return "";
    }

    @Override
    public boolean isActive() {
        return this.active.get();
    }

    @Override
    public void close() {
        this.active.set(false);
    }

    @Override
    public Object getBean(String beanName) throws BeanException {
        return this.getBeanFactory().getBean(beanName);
    }

    protected abstract void postProcessBeanFactory(BeanFactory beanFactory);

    protected abstract void registerBeanPostProcessor(BeanFactory beanFactory);

    protected abstract void initApplicationEventPublisher();

    protected abstract void onRefresh();

    protected abstract void registerListener();

    protected abstract void finishRefresh();

}
