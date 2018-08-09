package io.pivotal.pccdemo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@Region(name = "book")
public class Book {
    @Id
    @javax.persistence.Id
    private String id;
    private String title;
    private String author_name;
    private String price;

    public Book() {

    }

    public Book(String id, String title, String author_name, String price) {
        this.id = id;
        this.title = title;
        this.author_name = author_name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
