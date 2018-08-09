package io.pivotal.pccdemo.repo;

import io.pivotal.pccdemo.domain.Book;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.data.gemfire.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "books")
public interface BookRepo extends GemfireRepository<Book, String> {

    @Query("SELECT * FROM /book o WHERE o.id = $1" )
    Book findBookById(String id);

}
