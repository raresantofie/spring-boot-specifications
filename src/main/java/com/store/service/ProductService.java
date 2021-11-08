package com.store.service;

import com.store.model.Product;
import com.store.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> findProducts(Specification<Product> productSpecification, Pageable pageRequest) {
        return productRepository.findAll(productSpecification, pageRequest);
    }
}
