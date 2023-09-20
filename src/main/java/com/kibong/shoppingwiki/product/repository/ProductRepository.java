package com.kibong.shoppingwiki.product.repository;

import com.kibong.shoppingwiki.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> getProductByProductName(String productName);
}
