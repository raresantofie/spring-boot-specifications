package com.store.repository;

import com.store.model.Category;
import com.store.model.Category_;
import com.store.model.Product;
import com.store.model.Product_;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductSpecification implements Specification<Product> {

    @Override
    public Specification<Product> and(Specification<Product> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Product> or(Specification<Product> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
