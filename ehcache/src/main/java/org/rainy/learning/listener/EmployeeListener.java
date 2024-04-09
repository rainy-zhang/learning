package org.rainy.learning.listener;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.rainy.learning.listener.entity.Employee;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
public class EmployeeListener implements CacheEventListener<Long, Employee> {

    @Override
    public void onEvent(CacheEvent<? extends Long, ? extends Employee> cacheEvent) {
        Long key = cacheEvent.getKey();
        Employee oldValue = cacheEvent.getOldValue();
        Employee newValue = cacheEvent.getNewValue();
        switch (cacheEvent.getType()) {
            case CREATED:
                log.info("cache created, key: {}, old: {}, new: {}", key, oldValue, newValue);
                break;
            case UPDATED:
                log.info("cache updated, key: {}, old: {}, new: {}", key, oldValue, newValue);
                break;
            case EXPIRED:
                log.info("cache expired, key: {}, old: {}, new: {}", key, oldValue, newValue);
                break;
            case REMOVED:
                log.info("cache removed, key: {}, old: {}, new: {}", key, oldValue, newValue);
                break;
            case EVICTED:
                log.info("cache evicted, key: {}, old: {}, new: {}", key, oldValue, newValue);
                break;
            default:
                log.error("unknown event type");
        }
    }
}
