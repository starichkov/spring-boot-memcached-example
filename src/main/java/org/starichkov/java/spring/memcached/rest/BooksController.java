package org.starichkov.java.spring.memcached.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.starichkov.java.spring.memcached.domain.dto.BookDto;
import org.starichkov.java.spring.memcached.domain.service.BookService;

/**
 * @author Vadim Starichkov
 * @since 16.07.2020 16:36
 */
@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookService service;

    @Autowired
    public BooksController(BookService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @GetMapping(path = "/isbn", params = {"isbn"})
    public BookDto getBook(@RequestParam("isbn") String isbn) {
        return service.getByISBN(isbn);
    }

    @PostMapping(params = {"title", "author", "isbn"})
    public BookDto createBook(@RequestParam("title") String title, @RequestParam("author") String author, @RequestParam("isbn") String isbn) {
        BookDto book = new BookDto();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        return service.create(book);
    }

    @PatchMapping(path = "/{id}", params = {"title"})
    public BookDto updateBook(@PathVariable("id") Long id, @RequestParam("title") String title) {
        BookDto book = service.get(id);
        book.setTitle(title);
        return service.update(book);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @DeleteMapping(path = "/isbn", params = {"isbn"})
    public void deleteBookByIsbn(@RequestParam("isbn") String isbn) {
        service.deleteByIsbn(isbn);
    }

    @DeleteMapping("/all")
    public void evictAll() {
        service.evictAll();
    }
}
