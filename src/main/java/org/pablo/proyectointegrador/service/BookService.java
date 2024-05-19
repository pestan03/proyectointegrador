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

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Optional<Book> getBookById(String id) {
    return bookRepository.findById(id);
  }

  public Book saveBook(Book book) {
    return bookRepository.save(book);
  }

  public void deleteBook(String id) {
    bookRepository.deleteById(id);
  }

  public void saveOrUpdateBook(Book book) {
    // Verificar si el libro ya existe por ID
    if (book.getId() != null && bookRepository.existsById(book.getId())) {
      // Actualizar el libro existente
      Book existingBook = bookRepository.findById(book.getId()).orElse(null);
      if (existingBook != null) {
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        bookRepository.save(existingBook);
      }
    } else {
      // Guardar nuevo libro
      bookRepository.save(book);
    }
  }

  public Object searchBooksByTitle(String title) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
