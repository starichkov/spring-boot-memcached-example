package org.starichkov.java.spring.memcached.config;

import net.spy.memcached.MemcachedClient;

/**
 * @author Vadim Starichkov
 * @since 27.07.2020 17:43
 */
public class BooksIdCache extends BaseMemcachedCache {

    public BooksIdCache(MemcachedClient client) {
        super(client);
    }

    @Override
    public String getName() {
        return Constants.CACHE_BOOKS_ID;
    }
}
