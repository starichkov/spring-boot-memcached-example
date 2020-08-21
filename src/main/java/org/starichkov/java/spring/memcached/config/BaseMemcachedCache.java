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
public abstract class BaseMemcachedCache implements Cache {

    private final MemcachedClient client;

    public BaseMemcachedCache(MemcachedClient client) {
        this.client = client;
    }

    @Override
    public Object getNativeCache() {
        return client;
    }

    @Override
    public ValueWrapper get(Object baseKey) {
        Object value = null;
        String key = getKey(baseKey);

        try {
            value = client.get(key);
        } catch (final Exception e) {
            log.warn(e.getMessage());
        }

        if (value == null) {
            log.info("Cache miss for key: '{}'", key);
            return null;
        }

        log.info("Cache hit for key: '{}'", key);

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
    public void put(Object baseKey, Object value) {
        if (value != null) {
            String key = getKey(baseKey);
            client.set(key, 100500, value);
            log.info("Cache put for key: '{}'", key);
        }
    }

    @Override
    public void evict(Object baseKey) {
        String key = getKey(baseKey);
        client.delete(key);
        log.info("Cache delete for key: '{}'", key);
    }

    @Override
    public void clear() {
        client.flush();
        log.info("Cache clear completed");
    }

    private String getKey(Object key) {
        return String.format("%s:%s", getName(), key);
    }
}
