package com.robinsingh.productservice.repository;

import com.mongodb.client.MongoDatabase;
import com.robinsingh.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
