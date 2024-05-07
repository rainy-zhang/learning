package org.rainy.minis.event.publisher;

import org.rainy.minis.event.ApplicationEvent;
import org.rainy.minis.event.listener.ApplicationListener;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class SimpleApplicationEventPublisher implements ApplicationEventPublisher {

    protected final List<ApplicationListener> listeners = new ArrayList<>();

    @Override
    public void publishEvent(ApplicationEvent event) {
        for (ApplicationListener listener : this.listeners) {
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void addApplicationListener(ApplicationListener listener) {
        this.listeners.add(listener);
    }

}
