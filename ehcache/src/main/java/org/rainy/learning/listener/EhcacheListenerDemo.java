package org.rainy.learning.listener;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.event.EventType;
import org.rainy.learning.listener.entity.Employee;

import java.util.concurrent.Executors;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
public class EhcacheListenerDemo {

    public static final UserManagedCache<Long, Employee> userManagedCache = UserManagedCacheBuilder.newUserManagedCacheBuilder(Long.class, Employee.class)
            .withEventExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(5))
            .withEventListeners(
                    CacheEventListenerConfigurationBuilder
                            .newEventListenerConfiguration(EmployeeListener.class, EventType.CREATED, EventType.REMOVED, EventType.UPDATED)
                            .asynchronous()
                            .unordered()
            )
            .withResourcePools(
                    ResourcePoolsBuilder.newResourcePoolsBuilder()
                            .heap(3, EntryUnit.ENTRIES)
            )
            .build(true);

    public static void test() {
        userManagedCache.put(1L, Employee.builder()
                .id(1)
                .name("zhangsan")
                .build());

        userManagedCache.put(1L, Employee.builder()
                .id(1)
                .name("zhangsan1")
                .build());

        userManagedCache.remove(1L);
    }

}
