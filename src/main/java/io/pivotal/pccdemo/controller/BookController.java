package io.pivotal.pccdemo.controller;


import io.pivotal.pccdemo.domain.Book;
import io.pivotal.pccdemo.jpa.repo.BookJpaRepository;
import io.pivotal.pccdemo.repo.BookRepo;
import io.pivotal.pccdemo.service.BookSearchService;
import org.apache.geode.cache.Operation;
import org.apache.geode.cache.query.CqEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@DependsOn({"gemfireCache"})
public class BookController {

    @Autowired
    BookRepo book;

    @Autowired
    BookJpaRepository bookJpaRepository;

    @Autowired
    BookSearchService bookSearchService;

    CacheUtil cacheUtil = new CacheUtil();

    @RequestMapping(value = "/")
    public String flush(Model model) {
        book.deleteById("PCF 3.0");     // for cq demo
        book.deleteAll();
        cacheUtil.commonAttr(model, book, false);
        return "/pccdemo/index";
    }

    @Transactional
    @RequestMapping(method = RequestMethod.PUT, value = "/put")
    public String putBook(String title, String price, String author_name, Model model) { ;
        Book bookObject = new Book();
        bookObject.setTitle(title);
        bookObject.setPrice(price);
        if(title.equals("PCF 3.0")) {       // for cq demo
            bookObject.setId(title);
        } else {
            bookObject.setId(java.util.UUID.randomUUID().toString());
        }
        bookObject.setAuthor_name(author_name);
        book.save(bookObject);
        cacheUtil.commonAttr(model, book, true);
        return "/pccdemo/index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/book")
    public String getBookById(@RequestParam(value = "id", required = true) String id, Model model) {

        long startTime = System.currentTimeMillis();
        Book bookObject = bookSearchService.getBookById(id);
        long elapsedTime = System.currentTimeMillis();

        Boolean isCacheMiss = bookSearchService.isCacheMiss();

        model.addAttribute("title", bookObject.getTitle());
        model.addAttribute("price", bookObject.getPrice());
        model.addAttribute("author_name", bookObject.getAuthor_name());
        model.addAttribute("ds", isCacheMiss);
        model.addAttribute("time", elapsedTime - startTime);
        cacheUtil.commonAttr(model, book, false);

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
        cacheUtil.commonAttr(model, book, false);

        return "/pccdemo/index";
    }
}

class CacheUtil {
    public void commonAttr(Model model, BookRepo book, boolean sleep) {
        model.addAttribute("cachecount", book.count());

        if(sleep) {
            try{

                Thread.sleep(3000);

            }catch(InterruptedException e){}
        }

        model.addAttribute("cqstatus", new BookCq().cqstatus);
    }
}

@Component
class BookCq {
    public static boolean cqstatus = false;

    @ContinuousQuery(name = "PCFQuery", query = "SELECT * FROM /book b WHERE b.title='PCF 3.0'", durable = true)
    public void handleBookEvent(CqEvent event) {
        System.out.println("*********Logging CQ Event from Lisntener!!!*********");
        Operation queryOperation = event.getQueryOperation();
        if (queryOperation.isUpdate())
        {
            // update data on the screen for the trade order . . .
            System.out.println("*********UPDATED*********");
            cqstatus = true;
        }
        else if (queryOperation.isCreate())
        {
            // add the trade order to the screen . . .
            System.out.println("*********CREATED*********");
            cqstatus = true;
        }
        else if (queryOperation.isDestroy())
        {
            // remove the trade order from the screen . . .
            System.out.println("*********DESTROYED*********");
            cqstatus = false;
        }
    }
}