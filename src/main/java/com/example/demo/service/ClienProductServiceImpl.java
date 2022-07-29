package com.example.demo.service;

import com.example.demo.common.MapperUtils;
import com.example.demo.model.Product;
import com.example.demo.model.ProductReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienProductServiceImpl implements ClienProductService {
    @Autowired
    private ProductService productService;

    @Override
    public Page<ProductReponse> getListProductPage(Pageable pageable) {
        Page<Product> productPage = productService.getListProduct(pageable);
        List<ProductReponse> productReponses = getProductReponses(productPage);
        return new PageImpl<>(productReponses, pageable, productPage.getTotalElements());
    }

    private List<ProductReponse> getProductReponses(Page<Product> productPage) {
        List<ProductReponse> productReponses = new ArrayList<>();
        for (Product product : productPage) {
            ProductReponse productReponse = MapperUtils.copyFrom(product, ProductReponse.class);
            productReponses.add(productReponse);
        }
        return productReponses;
    }


}
