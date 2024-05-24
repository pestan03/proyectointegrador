package org.pablo.proyectointegrador.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.pablo.proyectointegrador.Model.Book;
import org.pablo.proyectointegrador.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

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

    public List<Book> searchBook(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            // return all books or an empty list depending on your requirements
            return new ArrayList<>();
        }
        // create regex pattern and search
        Pattern pattern = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(pattern));
        return mongoTemplate.find(query, Book.class);
    }
}
