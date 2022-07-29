package com.example.demo.service;


import com.example.demo.model.ProductReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienProductService {
Page<ProductReponse> getListProductPage(Pageable pageable);
}
