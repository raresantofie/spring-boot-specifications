package com.store.controller;

import com.store.model.Product;
import com.store.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Page<Product> searchProducts(
            @IsProductSpecification Specification<Product> productSpecification,
            @PageableDefault Pageable pageable) {
         return productService.findProducts(productSpecification, pageable);
    }
}
