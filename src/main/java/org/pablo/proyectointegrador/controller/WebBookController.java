package org.pablo.proyectointegrador.controller;

import org.pablo.proyectointegrador.Model.Book;
import org.pablo.proyectointegrador.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/web/books";
    }


    @GetMapping("/search")
    public String searchBook(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book-search";
    }


    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable String id, Model model) {
        return bookService
                .getBookById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    return "edit-book";
                })
                .orElse("redirect:/web/books"); // o mostrar un mensaje de error
    }

    @PostMapping("/update/{id}")
    public String updateBook(
            @PathVariable String id,
            @ModelAttribute Book bookDetails
    ) {
        return bookService
                .getBookById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setIsbn(bookDetails.getIsbn());
                    bookService.saveBook(book);
                    return "redirect:/web/books";
                })
                .orElse("redirect:/web/books"); // o mostrar un mensaje de error
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.deleteBook(id);
        return "redirect:/web/books";
    }

}
