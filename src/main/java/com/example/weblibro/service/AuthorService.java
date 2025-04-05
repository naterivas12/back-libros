package com.example.weblibro.service;

import com.example.weblibro.model.Author;
import com.example.weblibro.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AuthorService {
    
    @Autowired
    private AuthorRepository authorRepository;
    
    public Flux<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    
    public Mono<Author> getAuthorById(String id) {
        return authorRepository.findById(id);
    }
    
    public Mono<Author> createAuthor(Author author) {
        return authorRepository.save(author);
    }
    
    public Mono<Author> updateAuthor(String id, Author authorDetails) {
        return authorRepository.findById(id)
            .flatMap(existingAuthor -> {
                existingAuthor.setName(authorDetails.getName());
                existingAuthor.setGender(authorDetails.getGender());
                return authorRepository.save(existingAuthor);
            });
    }
    
    public Mono<Void> deleteAuthor(String id) {
        return authorRepository.deleteById(id);
    }
}
