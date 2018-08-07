package com.example.demo;

import org.apache.geode.cache.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    com.example.demo.BookJpaRepo bookJpaRepo;

    @Autowired
    com.example.demo.BookPccRepo bookPccRepo;

    @Autowired
    BookSearchService bookSearchService;

    public String searchBookByEmail(@RequestParam(value = "id", required = true) int id) {

        long startTime = System.currentTimeMillis();
        Book book = bookSearchService.getBookById(id);
        long elapsedTime = System.currentTimeMillis();
        Boolean isCacheMiss = bookSearchService.isCacheMiss();
        String sourceFrom = isCacheMiss ? "MySQL" : "PCC";

        return String.format("Result [<b>%1$s</b>] <br/>"
                + "Cache Miss [<b>%2$s</b>] <br/>"
                + "Read from [<b>%3$s</b>] <br/>"
                + "Elapsed Time [<b>%4$s ms</b>]%n", book, isCacheMiss, sourceFrom, (elapsedTime - startTime));
    }
}
