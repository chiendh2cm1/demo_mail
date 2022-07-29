package com.example.demo.service;

import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
Page<Product> getListProduct (Pageable pageable);
boolean updateProduct(long id, Product product);
Product getProductById(long id);
List<Product> getList();
Product save(Product product);
}
