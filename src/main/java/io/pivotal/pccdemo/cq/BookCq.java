package io.pivotal.pccdemo.cq;

import org.apache.geode.cache.query.CqEvent;
import org.springframework.data.gemfire.listener.annotation.ContinuousQuery;
import org.springframework.stereotype.Component;

@Component
public class BookCq {

    @ContinuousQuery(name = "PCFQuery", query = "SELECT * FROM /book b WHERE b.title='PCF 3.0'", durable = true)
    public void handleBookEnvent(CqEvent event) {
        System.out.println("*********Logging CQ Event*********");
    }
}
