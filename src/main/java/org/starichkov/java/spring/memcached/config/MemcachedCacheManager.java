package org.starichkov.java.spring.memcached.config;

import net.spy.memcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Vadim Starichkov
 * @since 20.07.2020 11:50
 */
public class MemcachedCacheManager extends AbstractCacheManager {

    private final Collection<Cache> caches;

    public MemcachedCacheManager(MemcachedClient client, String... cacheNames) {
        this.caches = generateCachesForNames(client, cacheNames);
        initializeCaches();
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return caches;
    }

    private Collection<Cache> generateCachesForNames(MemcachedClient client, String... cacheNames) {
        Collection<Cache> caches = new ArrayList<>(cacheNames.length);
        for (String cacheName : cacheNames) {
            caches.add(new BaseMemcachedCache(client) {
                @Override
                public String getName() {
                    return cacheName;
                }
            });
        }
        return Collections.unmodifiableCollection(caches);
    }
}
