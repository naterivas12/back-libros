package com.example.weblibro.controller;

import com.example.weblibro.model.Author;
import com.example.weblibro.model.Book;
import com.example.weblibro.repository.AuthorRepository;
import com.example.weblibro.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/seed")
@CrossOrigin(origins = "*")
public class SeedController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public Mono<ResponseEntity<String>> seedDatabase() {
        return authorRepository.deleteAll()
            .then(bookRepository.deleteAll())
            .then(createAuthorsAndBooks())
            .thenReturn(ResponseEntity.ok("Proceso de seed completado"));
    }

    private Mono<Void> createAuthorsAndBooks() {
        return Flux.fromIterable(getInitialAuthors())
            .flatMap(author -> authorRepository.save(author)
                .flatMap(savedAuthor -> Flux.fromIterable(getBooksForAuthor(savedAuthor))
                    .flatMap(book -> bookRepository.save(book))
                    .then(Mono.empty())))
            .then();
    }

    private List<Author> getInitialAuthors() {
        return Arrays.asList(
            createAuthor("Gabriel García Márquez", "Masculino"),
            createAuthor("J.R.R. Tolkien", "Masculino"),
            createAuthor("George Orwell", "Masculino"),
            createAuthor("Miguel de Cervantes", "Masculino"),
            createAuthor("J.K. Rowling", "Femenino")
        );
    }

    private Author createAuthor(String name, String gender) {
        Author author = new Author();
        author.setName(name);
        author.setGender(gender);
        return author;
    }

    private List<Book> getBooksForAuthor(Author author) {
        switch (author.getName()) {
            case "Gabriel García Márquez":
                return Arrays.asList(
                    createBook("Cien años de soledad", 
                        "Una saga familiar que narra la historia de los Buendía en Macondo", 
                        1967, author.getId(), true),
                    createBook("El amor en los tiempos del cólera", 
                        "Una historia de amor que atraviesa más de medio siglo", 
                        1985, author.getId(), true)
                );
            case "J.R.R. Tolkien":
                return Arrays.asList(
                    createBook("El Señor de los Anillos",
                        "Una épica historia de fantasía sobre la lucha contra el mal en la Tierra Media",
                        1954, author.getId(), true),
                    createBook("El Hobbit",
                        "La aventura de Bilbo Bolsón que precede a El Señor de los Anillos",
                        1937, author.getId(), true)
                );
            case "George Orwell":
                return Arrays.asList(
                    createBook("1984",
                        "Una distopía que explora temas de vigilancia gubernamental y control social",
                        1949, author.getId(), true),
                    createBook("Rebelión en la granja",
                        "Una alegoría sobre la corrupción del poder",
                        1945, author.getId(), true)
                );
            case "Miguel de Cervantes":
                return Arrays.asList(
                    createBook("Don Quijote de la Mancha",
                        "Las aventuras de un hidalgo que enloquece leyendo novelas de caballería",
                        1605, author.getId(), true),
                    createBook("Novelas ejemplares",
                        "Colección de doce novelas cortas",
                        1613, author.getId(), true)
                );
            case "J.K. Rowling":
                return Arrays.asList(
                    createBook("Harry Potter y la piedra filosofal",
                        "El primer año de Harry Potter en la escuela de magia Hogwarts",
                        1997, author.getId(), true),
                    createBook("Harry Potter y la cámara secreta",
                        "El segundo año de Harry Potter en Hogwarts",
                        1998, author.getId(), true)
                );
            default:
                return Collections.emptyList();
        }
    }

    private Book createBook(String title, String description, 
                          Integer year, String authorId, boolean published) {
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setYear(year);
        book.setAuthorId(authorId);
        book.setPublished(published);
        book.setRegistrationDate(LocalDateTime.now());
        return book;
    }
}
