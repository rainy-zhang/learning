package org.rainy.minis.event;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public interface ApplicationEventPublisher {

    void publishEvent(ApplicationEvent event);

}
