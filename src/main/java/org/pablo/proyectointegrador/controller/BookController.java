package org.pablo.proyectointegrador.controller;

import java.util.List;
import java.util.Optional;
import org.pablo.proyectointegrador.Model.Book;
import org.pablo.proyectointegrador.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookService bookService;

  @GetMapping
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/{id}")
  public Optional<Book> getBookById(@PathVariable String id) {
    return bookService.getBookById(id);
  }

  @PostMapping
  public Book createBook(@RequestBody Book book) {
    return bookService.saveBook(book);
  }

  @PutMapping("/{id}")
  public Optional<Book> updateBook(
    @PathVariable String id,
    @RequestBody Book bookDetails
  ) {
    return bookService
      .getBookById(id)
      .map(book -> {
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        return bookService.saveBook(book);
      });
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable String id) {
    bookService.deleteBook(id);
  }
}
