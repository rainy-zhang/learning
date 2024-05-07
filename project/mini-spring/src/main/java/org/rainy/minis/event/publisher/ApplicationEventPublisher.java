package org.rainy.minis.event.publisher;

import org.rainy.minis.event.ApplicationEvent;
import org.rainy.minis.event.listener.ApplicationListener;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

    void addApplicationListener(ApplicationListener listener);

}
