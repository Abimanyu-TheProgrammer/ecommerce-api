package com.example.shopAPI.product.controller;

import com.example.shopAPI.product.dto.ProductUpdateDTO;
import com.example.shopAPI.product.dto.ProductCreateDTO;
import com.example.shopAPI.product.interfaces.ProductService;
import com.example.shopAPI.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object>> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "ASC") String sort
    ){
        try {
            List<Product> products;
            Pageable pageable;
            if (sort.equals("DESC")) {
                pageable = PageRequest.of(page, size, Sort.by("price").descending());
            } else {
                pageable = PageRequest.of(page, size, Sort.by("price").ascending());
            }

            Page<Product> data = productService.findAll(search, pageable);
            products = data.getContent();

            Map<String, Object> response = new HashMap<>();
            response.put("products", products);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> findById(@PathVariable String id){
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/product")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> create(@RequestBody ProductCreateDTO productCreateDTO) {
        return new ResponseEntity<>(productService.create(productCreateDTO), HttpStatus.CREATED);
    }

    @PutMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody ProductUpdateDTO productUpdateDTO){
        return new ResponseEntity<>(productService.update(productUpdateDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> delete(@PathVariable String id){
        return new ResponseEntity<>(productService.delete(id), HttpStatus.OK);

    }
}
