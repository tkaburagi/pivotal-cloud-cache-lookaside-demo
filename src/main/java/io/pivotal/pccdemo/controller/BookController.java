package io.pivotal.pccdemo.controller;


import io.pivotal.pccdemo.domain.Book;
import io.pivotal.pccdemo.jpa.repo.BookJpaRepository;
import io.pivotal.pccdemo.repo.BookRepo;
import io.pivotal.pccdemo.service.BookSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
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
    public String showDB() throws Exception {
        StringBuilder result = new StringBuilder();

        List<Book> booklist = bookJpaRepository.findAll();

        return "First Book is show here: <br/>" + booklist.get(0).getTitle();
    }


    @RequestMapping(value = "/")
    public String flush() {
        book.deleteAll();
        return "/pccdemo/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/book")
    public String getBookById(@RequestParam(value = "id", required = true) String id, Model model) {

        long startTime = System.currentTimeMillis();
        Book bookObject = bookSearchService.getBookById(id);
        long elapsedTime = System.currentTimeMillis();

        Boolean isCacheMiss = bookSearchService.isCacheMiss();
        book.

        model.addAttribute("title", bookObject.getTitle());
        model.addAttribute("price", bookObject.getPrice());
        model.addAttribute("author_name", bookObject.getAuthor_name());
        model.addAttribute("ds", isCacheMiss);
        model.addAttribute("time", elapsedTime - startTime);
        model.addAttribute("cachecount", book.count());

        return "/pccdemo/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bookdb")
    public String getBookByIdDb(@RequestParam(value = "id", required = true) String id, Model model) {

        long startTime = System.currentTimeMillis();
        Book bookObject = bookJpaRepository.findBookById(id);
        long elapsedTime = System.currentTimeMillis();

        model.addAttribute("title", bookObject.getTitle());
        model.addAttribute("price", bookObject.getPrice());
        model.addAttribute("author_name", bookObject.getAuthor_name());
        model.addAttribute("ds", true);
        model.addAttribute("time", elapsedTime - startTime);

        return "/pccdemo/index";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/allbooks")
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