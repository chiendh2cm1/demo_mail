package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {
    Product insert(Product product);

    List<Product> getAll();

    Product getById(long id);

    Page<Product> findProductPage(Pageable pageable);

    boolean updateProduct(long id, Product product);

    void deleteProduct(long id);
}
