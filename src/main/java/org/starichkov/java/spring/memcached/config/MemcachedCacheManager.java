package org.starichkov.java.spring.memcached.config;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Vadim Starichkov
 * @since 20.07.2020 11:50
 */
public class MemcachedCacheManager extends AbstractCacheManager {

    private final Cache memcachedCache;

    public MemcachedCacheManager(Cache memcachedCache) {
        this.memcachedCache = memcachedCache;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Collections.singleton(memcachedCache);
    }
}
