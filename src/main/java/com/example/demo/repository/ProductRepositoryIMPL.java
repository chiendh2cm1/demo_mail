package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryIMPL implements ProductRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Product insert(Product product) {
        return mongoTemplate.insert(product);
    }

    @Override
    public List<Product> getAll() {
        return mongoTemplate.findAll(Product.class);
    }

    @Override
    public Product getById(long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query, Product.class);
    }


}
