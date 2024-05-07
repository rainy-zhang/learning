package org.rainy.minis.context;

import org.rainy.minis.beans.factory.BeanFactory;
import org.rainy.minis.beans.support.BeanFactoryPostProcessor;
import org.rainy.minis.core.env.Environment;
import org.rainy.minis.core.env.EnvironmentCapable;
import org.rainy.minis.event.publisher.ApplicationEventPublisher;
import org.rainy.minis.exception.BeanException;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface ApplicationContext extends EnvironmentCapable, ApplicationEventPublisher {

    String getApplicationName();

    long getStartupDate();

    void setEnvironment(Environment environment);

    Environment getEnvironment();

    BeanFactory getBeanFactory();

    void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

    void refresh();

    boolean isActive();

    void close();

    Object getBean(String beanName) throws BeanException;

    String[] getBeanNames();
}
