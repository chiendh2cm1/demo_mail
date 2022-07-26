package com.example.demo.repository;

import com.example.demo.model.Product;

import java.util.List;

public interface ProductRepository {
    Product insert(Product product);

    List<Product> getAll();

    Product getById(long id);
}
