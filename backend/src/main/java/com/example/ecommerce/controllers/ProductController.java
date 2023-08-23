package com.example.ecommerce.controllers;

import com.example.ecommerce.model.Category;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(params = "product")
    public ResponseEntity<Product> getProductById(@RequestParam Long product) {
        Product foundProduct = productService.getProductById(product);
        if (foundProduct != null) {
            return new ResponseEntity<>(foundProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(params = "category")
    public ResponseEntity<Product> createProduct(@RequestParam Long category, @RequestBody Product product) {
        Category foundCategory = categoryService.getCategoryById(category);
        if (foundCategory != null) {
            product.setCategory(foundCategory);
            Product createdProduct = productService.createProduct(product);
            return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(params = "product")
    public ResponseEntity<Product> updateProduct(@RequestParam Long product, @RequestBody Product updatedProduct) {
        Product existingProduct = productService.getProductById(product);
        if (existingProduct != null) {
            updatedProduct.setId(product);
            Product updated = productService.updateProduct(updatedProduct);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(params = "product")
    public ResponseEntity<Void> deleteProduct(@RequestParam Long product) {
        Product existingProduct = productService.getProductById(product);
        if (existingProduct != null) {
            productService.deleteProduct(product);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
