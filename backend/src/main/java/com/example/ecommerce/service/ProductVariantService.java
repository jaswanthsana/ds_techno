package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ProductVariant;
import com.example.ecommerce.repository.ProductVariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductVariantService {

    private final ProductVariantRepository productVariantRepository;

    @Autowired
    public ProductVariantService(ProductVariantRepository productVariantRepository) {
        this.productVariantRepository = productVariantRepository;
    }

    public ProductVariant createVariant(ProductVariant variant) {
        return productVariantRepository.create(variant);
    }

    public ProductVariant getVariantBySku(String sku) {
        return productVariantRepository.findBySku(sku);
    }

    public List<ProductVariant> getVariantsByProduct(Product product) {
        return productVariantRepository.findByProduct(product);
    }

    public List<ProductVariant> getAllVariants() {
        return productVariantRepository.findAll();
    }

    public ProductVariant updateVariant(ProductVariant variant) {
        return productVariantRepository.update(variant);
    }

    public void deleteVariant(String sku) {
        productVariantRepository.delete(sku);
    }
}
