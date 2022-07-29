package com.example.demo.repository;

import com.example.demo.model.Product;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    @Override
    public Page<Product> findProductPage(Pageable pageable) {
        Query query = new Query();
        query.with(pageable);
        int startRecord = pageable.getPageNumber() * pageable.getPageSize();
        query.skip(startRecord).limit(pageable.getPageSize());
        List<Product> products = mongoTemplate.findAll(Product.class);
        long count = mongoTemplate.count(query.skip(-1).limit(-1), Product.class);
        return new PageImpl<>(products, pageable, count);
    }

    @Override
    public boolean updateProduct(long id, Product product) {
        Query query = getQuery(id);
        Update update = new Update();
        update.set("product_name", product.getProduct_name());
        update.set("price", product.getPrice());
        update.set("description", product.getDescription());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Product.class);
        return updateResult.getModifiedCount() > 0;
    }

    private Query getQuery(long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        return query;
    }
}
