package org.starichkov.java.spring.memcached.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.starichkov.java.spring.memcached.config.CacheEvictionManager;
import org.starichkov.java.spring.memcached.config.Constants;
import org.starichkov.java.spring.memcached.domain.entity.Book;
import org.starichkov.java.spring.memcached.domain.dto.BookDto;
import org.starichkov.java.spring.memcached.domain.mapper.BookMapper;
import org.starichkov.java.spring.memcached.domain.repository.BookRepository;

/**
 * @author Vadim Starichkov
 * @since 16.07.2020 16:28
 */
@Service
@Slf4j
public class BookService {

    private final BookRepository repository;
    private final CacheEvictionManager cacheEvictionManager;
    private final BookMapper mapper;

    @Autowired
    public BookService(BookRepository repository, CacheEvictionManager cacheEvictionManager) {
        this.repository = repository;
        this.cacheEvictionManager = cacheEvictionManager;
        this.mapper = BookMapper.INSTANCE;
    }

    @Cacheable(cacheNames = Constants.CACHE_BOOKS_ID, key = "#id", unless = "#result == null")
    public BookDto get(Long id) {
        return mapper.map(repository.findById(id).orElse(null));
    }

    @Cacheable(cacheNames = Constants.CACHE_BOOKS_ISBN, key = "#isbn", unless = "#result == null")
    public BookDto getByISBN(String isbn) {
        return mapper.map(repository.findByIsbn(isbn).orElse(null));
    }

    //@CachePut(key = "#result.id", unless = "#result == null")
    public BookDto create(BookDto book) {
        Book entity = mapper.map(book);
        return mapper.map(repository.save(entity));
    }

    @Caching(put = {
            @CachePut(cacheNames = Constants.CACHE_BOOKS_ID, key = "#book.id", unless = "#result == null"),
            @CachePut(cacheNames = Constants.CACHE_BOOKS_ISBN, key = "#book.isbn", unless = "#result == null")
    })
    public BookDto update(BookDto book) {
        Book entity = repository.findById(book.getId())
                .orElseGet(() -> {
                    Book newBook = new Book();
                    newBook.setId(book.getId());
                    return newBook;
                });
        entity.setAuthor(book.getAuthor());
        entity.setIsbn(book.getIsbn());
        entity.setTitle(book.getTitle());
        return mapper.map(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.findById(id).ifPresent(this::deleteBook);
    }

    public void deleteByIsbn(String isbn) {
        repository.findByIsbn(isbn).ifPresent(this::deleteBook);
    }

    private void deleteBook(Book book) {
        cacheEvictionManager.evict(book.getId(), book.getIsbn());
        repository.delete(book);
    }

    public void evictAll() {
        cacheEvictionManager.evictAll();
    }
}
