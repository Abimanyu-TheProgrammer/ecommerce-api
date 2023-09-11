package com.example.shopAPI.product.service;

import com.example.shopAPI.product.dto.ProductCreateDTO;
import com.example.shopAPI.product.dto.ProductUpdateDTO;
import com.example.shopAPI.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    public Page<Product> findAll(String search, Pageable pageable);
    public Product findById(String id);
    public Product create(ProductCreateDTO productCreateDTO);
    public Product update(ProductUpdateDTO productUpdateDTO, String id);
    public Product delete(String id);
}
