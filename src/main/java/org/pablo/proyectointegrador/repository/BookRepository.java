package org.pablo.proyectointegrador.repository;

import java.util.List;
import org.pablo.proyectointegrador.Model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
  List<Book> findByTitleContainingIgnoreCase(String title);
}
