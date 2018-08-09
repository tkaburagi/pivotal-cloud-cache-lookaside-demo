package io.pivotal.pccdemo.service;

import io.pivotal.pccdemo.domain.Book;
import io.pivotal.pccdemo.jpa.repo.BookJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookSearchService {

    @Autowired
    BookJpaRepository bookJpaRepository;

    private volatile boolean cacheMiss = false;

    public boolean isCacheMiss() {
        boolean isCacheMiss = this.cacheMiss;
        this.cacheMiss = false;
        return isCacheMiss;
    }

    protected void setCacheMiss() {
        this.cacheMiss = true;
    }

    @Cacheable(value = "book")
    public Book getBookById(String id) {

        setCacheMiss();

        Book book = bookJpaRepository.findBookById(id);

        return book;
    }

}