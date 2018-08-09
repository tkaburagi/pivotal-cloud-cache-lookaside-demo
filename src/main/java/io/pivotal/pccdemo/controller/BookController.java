package io.pivotal.pccdemo.controller;


import io.pivotal.pccdemo.domain.Book;
import io.pivotal.pccdemo.jpa.repo.BookJpaRepository;
import io.pivotal.pccdemo.repo.BookRepo;
import io.pivotal.pccdemo.service.BookSearchService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@DependsOn({"gemfireCache"})
public class BookController {

    @Autowired
    BookRepo book;

    @Autowired
    BookJpaRepository bookJpaRepository;

    @Autowired
    BookSearchService bookSearchService;

    @SuppressWarnings("deprecation")
    @RequestMapping(method = RequestMethod.GET, path = "/showdb-book")
    @ResponseBody
    public String showDB() throws Exception {
        StringBuilder result = new StringBuilder();

        List<Book> booklist = bookJpaRepository.findAll();

        return "First Book is show here: <br/>" + booklist.get(0).getTitle();
    }


    @RequestMapping(method = RequestMethod.GET, path = "/book")
    @ResponseBody
    public String getBookById(@RequestParam(value = "id", required = true) String id) {

        StringBuilder result = new StringBuilder();
        long startTime = System.currentTimeMillis();
        Book bookObject = bookSearchService.getBookById(id);
        long elapsedTime = System.currentTimeMillis();

        Boolean isCacheMiss = bookSearchService.isCacheMiss();
        String from = isCacheMiss ? "Database" : "Pivotal Cloud Cache";

        String bookId = RandomStringUtils.randomAscii(5);

        return String.format("Result [<b>%1$s</b>] <br/>"
                    + "Cache Miss for Book [<b>%2$s</b>] <br/>"
                    + "Read from [<b>%3$s</b>] <br/>"
                    + "Elapsed Time [<b>%4$s ms</b>]%n", bookObject.getTitle(), isCacheMiss, from, (elapsedTime - startTime));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allbooks")
    @ResponseBody
    public String getAllBooks() {

        StringBuilder result = new StringBuilder();
        long startTime = System.currentTimeMillis();
        Iterable<Book> bookObject = book.findAll();
        long elapsedTime = System.currentTimeMillis();


        if (bookObject != null) {
            bookObject.forEach(item -> result.append(item + "</br>"));

            return String.format("Result [<b>%1$s</b>] <br/>"
                    + "Elapsed Time [<b>%2$s ms</b>]%n", result.toString(), (elapsedTime - startTime));
        }
        return "No Results Found.";
    }

}
