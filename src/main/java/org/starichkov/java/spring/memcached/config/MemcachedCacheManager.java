package org.starichkov.java.spring.memcached.config;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Vadim Starichkov
 * @since 20.07.2020 11:50
 */
public class MemcachedCacheManager extends AbstractCacheManager {

    private final Cache[] caches;

    public MemcachedCacheManager(Cache... caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return Arrays.asList(caches);
    }
}
