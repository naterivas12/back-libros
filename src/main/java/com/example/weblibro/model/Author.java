package com.example.weblibro.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "authors")
public class Author {
    @Id
    private String id;
    private String name;
    private String gender;
}
