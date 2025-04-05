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
        List<Book> books = new ArrayList<>();
        
        switch (author.getName()) {
            case "Gabriel García Márquez":
                books.addAll(Arrays.asList(
                    createBook("Cien años de soledad", 
                        "Una saga familiar que narra la historia de los Buendía en Macondo", 
                        1967, author.getId(), true),
                    createBook("El amor en los tiempos del cólera", 
                        "Una historia de amor que atraviesa más de medio siglo", 
                        1985, author.getId(), true),
                    createBook("Crónica de una muerte anunciada",
                        "Una historia sobre un asesinato anunciado en un pueblo",
                        1981, author.getId(), true),
                    createBook("El coronel no tiene quien le escriba",
                        "La historia de un coronel retirado que espera su pensión",
                        1961, author.getId(), true),
                    createBook("Memoria de mis putas tristes",
                        "La última novela del autor sobre un anciano periodista",
                        2004, author.getId(), true),
                    createBook("Del amor y otros demonios",
                        "Una historia de amor y exorcismo en la época colonial",
                        1994, author.getId(), true)
                ));
                break;

            case "J.R.R. Tolkien":
                books.addAll(Arrays.asList(
                    createBook("El Señor de los Anillos: La Comunidad del Anillo",
                        "Primera parte de la trilogía del Señor de los Anillos",
                        1954, author.getId(), true),
                    createBook("El Señor de los Anillos: Las Dos Torres",
                        "Segunda parte de la trilogía del Señor de los Anillos",
                        1954, author.getId(), true),
                    createBook("El Señor de los Anillos: El Retorno del Rey",
                        "Tercera parte de la trilogía del Señor de los Anillos",
                        1955, author.getId(), true),
                    createBook("El Hobbit",
                        "La aventura de Bilbo Bolsón que precede a El Señor de los Anillos",
                        1937, author.getId(), true),
                    createBook("El Silmarillion",
                        "La historia de la creación de la Tierra Media",
                        1977, author.getId(), true)
                ));
                break;

            case "J.K. Rowling":
                books.addAll(Arrays.asList(
                    createBook("Harry Potter y la piedra filosofal",
                        "El primer año de Harry Potter en Hogwarts",
                        1997, author.getId(), true),
                    createBook("Harry Potter y la cámara secreta",
                        "El segundo año de Harry Potter en Hogwarts",
                        1998, author.getId(), true),
                    createBook("Harry Potter y el prisionero de Azkaban",
                        "El tercer año de Harry Potter en Hogwarts",
                        1999, author.getId(), true),
                    createBook("Harry Potter y el cáliz de fuego",
                        "El cuarto año de Harry Potter en Hogwarts",
                        2000, author.getId(), true),
                    createBook("Harry Potter y la Orden del Fénix",
                        "El quinto año de Harry Potter en Hogwarts",
                        2003, author.getId(), true),
                    createBook("Harry Potter y el misterio del príncipe",
                        "El sexto año de Harry Potter en Hogwarts",
                        2005, author.getId(), true),
                    createBook("Harry Potter y las reliquias de la muerte",
                        "El último libro de la saga de Harry Potter",
                        2007, author.getId(), true)
                ));
                break;

            case "George Orwell":
                books.addAll(Arrays.asList(
                    createBook("1984",
                        "Una distopía que explora temas de vigilancia gubernamental y control social",
                        1949, author.getId(), true),
                    createBook("Rebelión en la granja",
                        "Una alegoría sobre la corrupción del poder",
                        1945, author.getId(), true),
                    createBook("Homenaje a Cataluña",
                        "Memorias de la Guerra Civil Española",
                        1938, author.getId(), true),
                    createBook("Los días de Birmania",
                        "Una novela sobre el imperialismo británico",
                        1934, author.getId(), true),
                    createBook("Sin blanca en París y Londres",
                        "Una memoria sobre la pobreza en Europa",
                        1933, author.getId(), true)
                ));
                break;

            case "Miguel de Cervantes":
                books.addAll(Arrays.asList(
                    createBook("Don Quijote de la Mancha - Primera Parte",
                        "Primera parte de las aventuras del ingenioso hidalgo",
                        1605, author.getId(), true),
                    createBook("Don Quijote de la Mancha - Segunda Parte",
                        "Segunda parte de las aventuras del ingenioso hidalgo",
                        1615, author.getId(), true),
                    createBook("Novelas ejemplares",
                        "Colección de doce novelas cortas",
                        1613, author.getId(), true),
                    createBook("Los trabajos de Persiles y Sigismunda",
                        "La última obra de Cervantes",
                        1617, author.getId(), true),
                    createBook("La Galatea",
                        "Primera novela de Cervantes",
                        1585, author.getId(), true),
                    createBook("Viaje del Parnaso",
                        "Poema alegórico",
                        1614, author.getId(), true)
                ));
                break;

            default:
                return Collections.emptyList();
        }
        
        // Agregar 100 libros adicionales con años variados
        Random random = new Random();
        String[] temas = {"Aventura", "Misterio", "Romance", "Fantasía", "Ciencia Ficción", "Historia", "Drama"};
        
        for (int i = 1; i <= 100; i++) {
            int año = 1900 + random.nextInt(124); // Libros entre 1900 y 2024
            String tema = temas[random.nextInt(temas.length)];
            books.add(createBook(
                "Libro " + i + " de " + author.getName(),
                "Una historia de " + tema.toLowerCase() + " por " + author.getName(),
                año,
                author.getId(),
                true
            ));
        }
        
        return books;
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
