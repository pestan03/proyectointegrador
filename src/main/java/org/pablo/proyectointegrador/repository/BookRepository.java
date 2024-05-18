package org.pablo.proyectointegrador.repository;

import org.pablo.proyectointegrador.Model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {}
