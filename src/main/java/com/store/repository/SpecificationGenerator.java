package com.store.repository;

import com.store.model.Category;
import com.store.model.Category_;
import com.store.model.Product;
import com.store.model.Product_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class SpecificationGenerator {

    private final Function<String, Specification<Product>> nameLike =
            name -> (root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.like(root.get(Product_.name), "%" + name + "%");

    private final Function<String, Specification<Product>> priceAbove =
            price -> (root, criteriaQuery, criteriaBuilder) ->
            criteriaBuilder.gt(root.get(Product_.price), Double.valueOf(price));

    private final Function<String, Specification<Product>> priceBelow =
            price -> (root, criteriaQuery, criteriaBuilder) ->
                    criteriaBuilder.lt(root.get(Product_.price), Double.valueOf(price));

    private final Function<String, Specification<Product>> category =
            categoryId -> (root, criteriaQuery, criteriaBuilder) -> {
                Join<Product, Category> categoryJoin = root.join(Product_.category);
                return criteriaBuilder.equal(categoryJoin.get(Category_.id), Integer.valueOf(categoryId));
            };

    private final Function<String, Specification<Product>> ratingAbove =
            rating -> (root, query, criteriaBuilder) -> criteriaBuilder.gt(root.get(Product_.rating), Double.valueOf(rating));

    public Map<String, Function<String, Specification<Product>>> filters =
            Map.of(
                    "name", nameLike,
                    "priceAbove", priceAbove,
                    "priceBelow", priceBelow,
                    "category", category,
                    "rating", ratingAbove);

    public Specification<Product> generate(Map<String, String> params){
        Specification<Product> productSpecification = new ProductSpecification();
        for(Map.Entry<String, String> element : params.entrySet()) {
            if (!filters.containsKey(element.getKey())) {
                log.error(String.format("Skipping filter %s, not supported", element.getKey()));
            } else {
                productSpecification = productSpecification
                        .and(filters.get(element.getKey()).apply(element.getValue()));
            }
        }

        return productSpecification;
    }
}
