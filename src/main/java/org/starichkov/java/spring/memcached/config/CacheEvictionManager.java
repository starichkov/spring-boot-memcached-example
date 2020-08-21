package org.starichkov.java.spring.memcached.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @author Vadim Starichkov
 * @since 28.07.2020 11:06
 */
@Service
@Slf4j
public class CacheEvictionManager {

    @Caching(evict = {
            @CacheEvict(cacheNames = Constants.CACHE_BOOKS_ID, key = "#id"),
            @CacheEvict(cacheNames = Constants.CACHE_BOOKS_ISBN, key = "#isbn")
    })
    public void evict(Long id, String isbn) {
        log.info("Cache: evicting by id {} and isbn {}", id, isbn);
    }

    @CacheEvict(cacheNames = Constants.CACHE_MAGAZINES_ID, key = "#id", cacheManager = "cacheManagerMgz")
    public void evictMagazine(Long id) {
        log.info("Cache: Magazine - evicting by id {}", id);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = Constants.CACHE_BOOKS_ID, allEntries = true),
            @CacheEvict(cacheNames = Constants.CACHE_BOOKS_ISBN, allEntries = true),
            @CacheEvict(cacheNames = Constants.CACHE_MAGAZINES_ID, allEntries = true, cacheManager = "cacheManagerMgz")
    })
    public void evictAll() {
        log.info("Cache: evicting all entries");
    }
}
