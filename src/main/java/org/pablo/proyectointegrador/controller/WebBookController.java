package org.pablo.proyectointegrador.controller;

import java.util.List;
import org.pablo.proyectointegrador.Model.Book;
import org.pablo.proyectointegrador.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/books")
public class WebBookController {

  @Autowired
  private BookService bookService;

  @GetMapping
  public String getAllBooks(Model model) {
    model.addAttribute("books", bookService.getAllBooks());
    return "book-list";
  }

  @GetMapping("/new")
  public String showNewBookForm(Model model) {
    model.addAttribute("book", new Book());
    return "book-form";
  }

  @PostMapping
  public String createBook(@ModelAttribute Book book) {
    bookService.saveBook(book);
    return "redirect:/web/books";
  }

  @GetMapping("/edit/{id}")
  public String showEditBookForm(@PathVariable String id, Model model) {
    bookService
      .getBookById(id)
      .ifPresent(book -> model.addAttribute("book", book));
    return "book-form";
  }

  @PostMapping("/update/{id}")
  public String updateBook(
    @PathVariable String id,
    @ModelAttribute Book bookDetails
  ) {
    bookService
      .getBookById(id)
      .ifPresent(book -> {
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        bookService.saveBook(book);
      });
    return "redirect:/web/books";
  }

  @GetMapping("/delete/{id}")
  public String deleteBook(@PathVariable String id) {
    bookService.deleteBook(id);
    return "redirect:/web/books";
  }

  @PostMapping(
    value = "/save",
    consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
  )
  public String saveBook(Book book) {
    // Lógica para guardar el libro en la base de datos
    bookService.saveBook(book);
    return "redirect:/web/books/list";
  }

  @GetMapping("/list")
  public String listBooks(Model model) {
    List<Book> books = bookService.getAllBooks();
    model.addAttribute("books", books);
    return "book-list"; // Nombre de la vista Thymeleaf para mostrar la lista de libros
  }
}
