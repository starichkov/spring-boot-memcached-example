package org.starichkov.java.spring.memcached.config;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.concurrent.Callable;

/**
 * @author Vadim Starichkov
 * @since 20.07.2020 12:06
 */
@Slf4j
abstract class BaseMemcachedCache implements Cache {

    private final MemcachedClient client;

    public BaseMemcachedCache(MemcachedClient client) {
        this.client = client;
    }

    @Override
    public Object getNativeCache() {
        return client;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = null;

        try {
            value = client.get(key.toString());
        } catch (final Exception e) {
            log.warn(e.getMessage());
        }

        if (value == null) {
            log.info("cache miss for key: " + key.toString());
            return null;
        }

        log.info("cache hit for key: " + key.toString());

        return new SimpleValueWrapper(value);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        log.warn("Unsupported GET called - Class<T>");
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        log.warn("Unsupported GET called - Callable<T>");
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        if (value != null) {
            client.set(key.toString(), 100500, value);
            log.info("cache put for key: " + key.toString());
        }
    }

    @Override
    public void evict(Object key) {
        client.delete(key.toString());
        log.info("cache delete for key: " + key.toString());

    }

    @Override
    public void clear() {
        client.flush();
        log.info("cache clear completed");
    }
}
