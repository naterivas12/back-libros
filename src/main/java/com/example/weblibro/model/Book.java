package com.example.weblibro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Document(collection = "books")
public class Book {
    @Id
    private String id;
    private String title;
    private String description;
    private Integer year;
    private String authorId;
    private boolean published;
    private LocalDateTime registrationDate;
}
