package com.example.demo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@JsonSerialize
@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BookJpaRepo extends JpaRepository<Book, Integer> {

    Book findBookById(final Integer id);
}