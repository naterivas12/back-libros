package com.example.weblibro.service;

import com.example.weblibro.model.Book;
import com.example.weblibro.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Mono<Book> getBookById(String id) {
        return bookRepository.findById(id);
    }
    
    public Mono<Book> createBook(Book book) {
        book.setRegistrationDate(LocalDateTime.now());
        return bookRepository.save(book);
    }
    
    public Mono<Book> updateBook(String id, Book bookDetails) {
        return bookRepository.findById(id)
            .flatMap(existingBook -> {
                existingBook.setTitle(bookDetails.getTitle());
                existingBook.setDescription(bookDetails.getDescription());
                existingBook.setYear(bookDetails.getYear());
                existingBook.setAuthorId(bookDetails.getAuthorId());
                existingBook.setPublished(bookDetails.isPublished());
                return bookRepository.save(existingBook);
            });
    }
    
    public Mono<Void> deleteBook(String id) {
        return bookRepository.deleteById(id);
    }
}
