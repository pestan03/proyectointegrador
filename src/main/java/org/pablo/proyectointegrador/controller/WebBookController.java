package org.pablo.proyectointegrador.controller;

import org.pablo.proyectointegrador.Model.Book;
import org.pablo.proyectointegrador.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/web/books") // Define la ruta base para todos los métodos en este controlador
public class WebBookController {

    @Autowired
    private BookService bookService; // Inyecta el servicio de libros

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks()); // Obtiene todos los libros y los añade al modelo
        return "index"; // Devuelve la vista de la lista de libros
    }

    @GetMapping("/new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book()); // Añade un nuevo libro al modelo
        return "book-form"; // Devuelve la vista del formulario de libros
    }

    @PostMapping("/save")
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book); // Guarda el libro
        return "redirect:/web/books"; // Redirige a la lista de libros
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam(value = "query", defaultValue = "") String query, Model model) {
        // Filtra los libros basándose en la consulta de búsqueda y los añade al modelo
        List<Book> books = bookService.getAllBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(query.toLowerCase())
                        || book.getAuthor().toLowerCase().contains(query.toLowerCase())
                        || book.getIsbn().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("books", books);
        return "book-search"; // Devuelve la vista de búsqueda de libros
    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable String id, Model model) {
        // Obtiene el libro por su id y lo añade al modelo
        return bookService
                .getBookById(id)
                .map(book -> {
                    model.addAttribute("book", book);
                    return "edit-book"; // Devuelve la vista de edición de libros
                })
                .orElse("redirect:/web/books"); // Si el libro no se encuentra, redirige a la lista de libros
    }

    @PostMapping("/update/{id}")
    public String updateBook(
            @PathVariable String id,
            @ModelAttribute Book bookDetails
    ) {
        // Actualiza el libro con los detalles proporcionados
        return bookService
                .getBookById(id)
                .map(book -> {
                    book.setTitle(bookDetails.getTitle());
                    book.setAuthor(bookDetails.getAuthor());
                    book.setIsbn(bookDetails.getIsbn());
                    bookService.saveBook(book);
                    return "redirect:/web/books"; // Redirige a la lista de libros
                })
                .orElse("redirect:/web/books"); // Si el libro no se encuentra, redirige a la lista de libros
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable String id) {
        bookService.deleteBook(id); // Elimina el libro
        return "redirect:/web/books"; // Redirige a la lista de libros
    }

}
