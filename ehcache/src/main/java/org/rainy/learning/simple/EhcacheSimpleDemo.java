package org.rainy.learning.simple;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *
 * </p>
 *
 * @author zhangyu
 */
@Slf4j
@Service
public class EhcacheSimpleDemo {

    private static final CacheConfiguration<Long, String> configuration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100))
            .build();

    private static final CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
            .withCache("MyCacheManager", configuration)
            .build(true);// 是否立刻初始化


    public static void createCache() {
        Cache<Long, String> userCache = cacheManager.createCache("UserCache", configuration);
        userCache.put(1L, "zhangsan");
        userCache.put(2L, "lisi");
    }

    public static void getCache() {
        Cache<Long, String> userCache = cacheManager.getCache("UserCache", Long.class, String.class);
        for (Cache.Entry<Long, String> entry : userCache) {
            log.info("find from ehcache: {}:{}", entry.getKey(), entry.getValue());
        }
    }

    public static void removeCache() {
        cacheManager.removeCache("UserCache");
    }

    public static void close() {
        cacheManager.close();
    }

}
