package org.starichkov.java.spring.memcached.config;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
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

    @Bean("cacheResolver")
    @Override
    public CacheResolver cacheResolver() {
        return new SimpleCacheResolver(
                new MemcachedCacheManager(memcachedClient,
                        Constants.CACHE_BOOKS_ID, Constants.CACHE_BOOKS_ISBN, Constants.CACHE_MAGAZINES_ID)
        );
    }
}
