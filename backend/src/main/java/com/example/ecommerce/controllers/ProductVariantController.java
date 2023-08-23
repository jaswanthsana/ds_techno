package com.example.ecommerce.controllers;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductVariant;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.service.ProductVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/variants")
public class ProductVariantController {

    private final ProductVariantService productVariantService;
    private final ProductService productService;

    @Autowired
    public ProductVariantController(ProductVariantService productVariantService, ProductService productService) {
        this.productVariantService = productVariantService;
        this.productService = productService;
    }

    @GetMapping(params = "product")
    public ResponseEntity<List<ProductVariant>> getVariantsByProduct(@RequestParam Long product) {
        Product foundProduct = productService.getProductById(product);
        if (foundProduct != null) {
            List<ProductVariant> variants = productVariantService.getVariantsByProduct(foundProduct);
            return new ResponseEntity<>(variants, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(params = {"product", "sku"})
    public ResponseEntity<ProductVariant> getVariantBySku(@RequestParam Long product, @RequestParam String sku) {
        ProductVariant foundVariant = productVariantService.getVariantBySku(sku);
        if (foundVariant != null && foundVariant.getProduct().getId().equals(product)) {
            return new ResponseEntity<>(foundVariant, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(params = "product")
    public ResponseEntity<ProductVariant> createVariant(@RequestParam Long product, @RequestBody ProductVariant variant) {
        Product foundProduct = productService.getProductById(product);
        if (foundProduct != null) {
            variant.setProduct(foundProduct);
            ProductVariant createdVariant = productVariantService.createVariant(variant);
            return new ResponseEntity<>(createdVariant, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(params = {"product", "sku"})
    public ResponseEntity<ProductVariant> updateVariant(@RequestParam Long product, @RequestParam String sku, @RequestBody ProductVariant updatedVariant) {
        ProductVariant existingVariant = productVariantService.getVariantBySku(sku);
        if (existingVariant != null && existingVariant.getProduct().getId().equals(product)) {
            updatedVariant.setSku(sku);
            ProductVariant updated = productVariantService.updateVariant(updatedVariant);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(params = {"product", "sku"})
    public ResponseEntity<Void> deleteVariant(@RequestParam Long product, @RequestParam String sku) {
        ProductVariant existingVariant = productVariantService.getVariantBySku(sku);
        if (existingVariant != null && existingVariant.getProduct().getId().equals(product)) {
            productVariantService.deleteVariant(sku);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
