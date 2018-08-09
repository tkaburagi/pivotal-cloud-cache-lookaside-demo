package io.pivotal.pccdemo.jpa.repo;


import io.pivotal.pccdemo.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("BookJpaRepository")
public interface BookJpaRepository extends JpaRepository<Book, String> {

    Book findBookById(final String id);

}
