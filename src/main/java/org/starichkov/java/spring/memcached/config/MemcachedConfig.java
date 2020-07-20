package org.starichkov.java.spring.memcached.config;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author Vadim Starichkov
 * @since 20.07.2020 11:40
 */
@Configuration
@Profile("!test")
@Slf4j
public class MemcachedConfig extends CachingConfigurerSupport {

    @Bean
    @Override
    public CacheManager cacheManager() {
        MemcachedClient client = memcachedClient();
        if (client == null) {
            return null;
        }
        Cache cache = memcachedCache(client);
        return new MemcachedCacheManager(cache);
    }

    @Value("${memcached.host}")
    private String host;

    @Value("${memcached.port}")
    private Integer port;

    private MemcachedClient memcachedClient() {
        try {
            return new MemcachedClient(new InetSocketAddress(host, port));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private Cache memcachedCache(MemcachedClient memcachedClient) {
        return new MemcachedCache(memcachedClient);
    }
}
