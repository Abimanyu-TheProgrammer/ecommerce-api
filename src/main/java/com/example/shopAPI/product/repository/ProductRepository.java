package com.example.shopAPI.product.repository;

import com.example.shopAPI.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{id: '?0'}")
    Product findProductById(String id);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByTitleLikeIgnoreCase(String title, Pageable pageable);

    public long count();
}
