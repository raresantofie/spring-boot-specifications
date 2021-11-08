package com.store;

import com.store.controller.SpecificationArgumentResolver;
import com.store.model.Category;
import com.store.model.Product;
import com.store.repository.CategoryRepository;
import com.store.repository.ProductRepository;
import com.store.repository.SpecificationGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
public class SpringDataSpecificationApplication implements CommandLineRunner, WebMvcConfigurer{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SpecificationGenerator specificationGenerator;

    @Autowired
    CategoryRepository categoryRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringDataSpecificationApplication.class, args);
    }

    @Override
    public void run(String... args) {
        dataGenerator();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver(specificationGenerator));
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    public void dataGenerator() {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i< 500; i++) {
            Category c = Category.builder().name(String.format("%s_%d", "Category", i)).build();
            c = categoryRepository.save(c);
            categories.add(c);
        }
        for (int i = 0; i< 500_000; i++) {
            Product p = Product.builder()
                    .price((double) i)
                    .name(String.format("%s_%d", "Prod", i))
                    .category(categories.get(ThreadLocalRandom.current().nextInt(0, 500)))
                    .rating(ThreadLocalRandom.current().nextDouble(0, 5))
                    .build();
            p = productRepository.save(p);
            System.out.println(p);
        }
    }
}
