package com.hutech.Shop.repository;

import com.hutech.Shop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryIdAndHideTrueOrderByOrderAsc(Long categoryId);
    Optional<Product> findByLink(String link);
}
