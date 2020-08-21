package org.starichkov.java.spring.memcached.config;

import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author Vadim Starichkov
 * @since 19.08.2020 17:09
 */
@Configuration
@Profile("!test")
@Slf4j
public class MemcachedClientConfig {

    @Value("${memcached.host}")
    private String host;

    @Value("${memcached.port}")
    private Integer port;

    @Bean
    public MemcachedClient memcachedClient() {
        try {
            return new MemcachedClient(new InetSocketAddress(host, port));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
