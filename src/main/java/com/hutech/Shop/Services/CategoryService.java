package com.hutech.Shop.Services;

import com.hutech.Shop.model.Category;
import com.hutech.Shop.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Trong CategoryService
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public Category findByLink(String link) {
        return categoryRepository.findByLink(link);
    }

    public ResponseEntity<Optional<Category>> getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            log.info("Category with ID {} found.", id);
            return ResponseEntity.ok(category);
        } else {
            log.warn("Category with ID {} not found.", id);
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Category> addCategory(@NotNull Category category) {
        if (category.getId() != null && categoryRepository.existsById(category.getId())) {
            log.warn("Category with ID {} already exists.", category.getId());
            return ResponseEntity.status(409).build(); // Conflict
        } else {
            Category savedCategory = categoryRepository.save(category);
            log.info("Category with ID {} created successfully.",
                    savedCategory.getId());
            return ResponseEntity.status(201).body(savedCategory); // Created
        }
    }

    public void updateCategory(@NotNull Category category) {
        Category existingCategory = categoryRepository.findById((long) category.getId())
                .orElseThrow(() -> new IllegalStateException("Category with ID " + category.getId() + " does not exist."));
        existingCategory.setName(category.getName());
        existingCategory.setMeta(category.getMeta());
        existingCategory.setLink(category.getLink());
        existingCategory.setHide(category.isHide());
        existingCategory.setOrder(category.getOrder());
        categoryRepository.saveAndFlush(existingCategory);
    }

    public ResponseEntity<Void> deleteCategoryById(Long id) {
        if (!categoryRepository.existsById(id)) {
            log.warn("Category with ID {} does not exist.", id);
            return ResponseEntity.notFound().build();
        }
        categoryRepository.deleteById(id);
        log.info("Category with ID {} deleted successfully.", id);
        return ResponseEntity.noContent().build(); // No Content
    }
}
