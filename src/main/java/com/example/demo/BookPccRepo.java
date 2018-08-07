package com.example.demo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@JsonSerialize
@RepositoryRestResource(collectionResourceRel = "book", path = "book")
public interface BookPccRepo extends GemfireRepository<Book, Long> {
}
