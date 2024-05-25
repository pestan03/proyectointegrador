package org.pablo.proyectointegrador.service;

import java.util.List;
import java.util.Optional;

import org.pablo.proyectointegrador.Model.Book;
import org.pablo.proyectointegrador.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Método para obtener todos los libros
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Método para obtener un libro por su id
    public Optional<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }

    // Método para guardar un libro
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Método para eliminar un libro
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
