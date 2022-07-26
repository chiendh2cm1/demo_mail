package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";
    @Id
    private Long id;
    @Indexed(unique = true)
    private String product_name;
    private double price;
    private String description;
    @Indexed(unique = true)
    private long category_id;
}
