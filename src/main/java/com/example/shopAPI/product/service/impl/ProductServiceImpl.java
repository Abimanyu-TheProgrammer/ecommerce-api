package com.example.shopAPI.product.service.impl;

import com.example.shopAPI.product.dto.ProductCreateDTO;
import com.example.shopAPI.product.dto.ProductUpdateDTO;
import com.example.shopAPI.product.service.ProductService;
import com.mongodb.MongoException;
import com.example.shopAPI.product.model.Product;
import com.example.shopAPI.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Page<Product> findAll(String search,Pageable pageable) {
        if(search == null) {
            return productRepository.findAll(pageable);
        } else {
            return productRepository.findByTitleLikeIgnoreCase(search, pageable);
        }
    }

    @Override
    public Product findById(String id) {
        if (!productRepository.existsById(id)) {
            throw new MongoException("Record not found");
        }
        return productRepository.findProductById(id);
    }

    @Override
    public Product create(ProductCreateDTO productCreateDTO) {
        Product newProduct = new Product(
                productCreateDTO.getTitle(),
                productCreateDTO.getDescription(),
                productCreateDTO.getPrice());
        return productRepository.save((newProduct));
    }

    @Override
    public Product update(ProductUpdateDTO productUpdateDTO, String id) {
        if(!productRepository.existsById(id)) {
            throw new MongoException("Record not found");
        }
        Product existingProduct = productRepository.findProductById(id);
        existingProduct.setTitle(productUpdateDTO.getTitle());
        existingProduct.setDescription(productUpdateDTO.getDescription());
        existingProduct.setPrice(productUpdateDTO.getPrice());
        return productRepository.save(existingProduct);
    }

    @Override
    public Product delete(String id) {
        if (!productRepository.existsById(id)){
            throw new MongoException("Record not found");
        }
        Product existingProduct = productRepository.findProductById(id);
        productRepository.delete(existingProduct);
        return existingProduct;
    }
}
