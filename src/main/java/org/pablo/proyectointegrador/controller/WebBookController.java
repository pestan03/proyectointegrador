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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/web/books")
public class WebBookController {

  @Autowired
  private BookService bookService;

  @GetMapping
  public String getAllBooks(
    Model model,
    @RequestParam(value = "title", required = false) String title
  ) {
    if (title != null && !title.isEmpty()) {
      model.addAttribute("books", bookService.searchBooksByTitle(title));
    } else {
      model.addAttribute("books", bookService.getAllBooks());
    }
    return "book-list";
  }

  @GetMapping("/new")
  public String showNewBookForm(Model model) {
    model.addAttribute("book", new Book());
    return "book-form";
  }

  @PostMapping
  public String saveBook(@ModelAttribute Book book) {
    bookService.saveOrUpdateBook(book);
    return "redirect:/web/books";
  }

  @GetMapping("/edit/{id}")
  public String showEditBookForm(@PathVariable String id, Model model) {
    bookService
      .getBookById(id)
      .ifPresent(book -> model.addAttribute("book", book));
    return "book-form";
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
  @GetMapping("/list")
  public String listBooks(Model model) {
    List<Book> books = bookService.getAllBooks();
    model.addAttribute("books", books);
    return "book-list"; // Nombre de la vista Thymeleaf para mostrar la lista de libros
  }
}
