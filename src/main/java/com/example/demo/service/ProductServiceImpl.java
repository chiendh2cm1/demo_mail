package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getListProduct(Pageable pageable) {
        return productRepository.findProductPage(pageable);
    }

    @Override
    public boolean updateProduct(long id, Product product) {
        return productRepository.updateProduct(id, product);
    }

    @Override
    public Product getProductById(long id) {
        return productRepository.getById(id);
    }

    @Override
    public List<Product> getList() {
        return productRepository.getAll();
    }

    @Override
    public Product save(Product product) {
        return productRepository.insert(product);
    }

    @Override
    public void deleleProduct(long id) {
        productRepository.deleteProduct(id);
    }
}
