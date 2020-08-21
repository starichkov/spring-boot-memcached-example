package org.starichkov.java.spring.memcached.config;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * @author Vadim Starichkov
 * @since 20.07.2020 11:40
 */
@Configuration
@Import(MemcachedClientConfig.class)
@Profile("!test")
@Slf4j
public class MemcachedConfig extends CachingConfigurerSupport {

    private final MemcachedClient memcachedClient;

    @Autowired
    public MemcachedConfig(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Bean("cacheManager")
    @Override
    public CacheManager cacheManager() {
        return new MemcachedCacheManager(memcachedClient, Constants.CACHE_BOOKS_ID, Constants.CACHE_BOOKS_ISBN);
    }

    @Bean("cacheManagerMgz")
    public CacheManager cacheManagerMgz() {
        return new MemcachedCacheManager(memcachedClient, Constants.CACHE_MAGAZINES_ID);
    }
}
