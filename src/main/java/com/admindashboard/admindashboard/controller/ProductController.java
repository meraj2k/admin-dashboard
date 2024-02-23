package com.admindashboard.admindashboard.controller;

import com.admindashboard.admindashboard.entity.Product;
import com.admindashboard.admindashboard.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> addProduct(@RequestBody Product product) {
        Product responseObj = productService.addProduct(product);

        Map<String, Long> response = new HashMap<>();
        response.put("id", responseObj.getProductId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
