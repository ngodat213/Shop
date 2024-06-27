package com.hutech.Shop.Services;

import com.hutech.Shop.model.Category;
import com.hutech.Shop.model.Product;
import com.hutech.Shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductByLink(String link){ return productRepository.findByLink(link);}

    public List<Product> getProductsByCategoryId(Long categoryId) {
        return
                productRepository.findByCategoryIdAndHideTrueOrderByOrderAsc(categoryId);
    }

    public Map<Category, List<Product>> getTop3ProductsByCategory() {
        List<Product> allProducts = productRepository.findAll();
        Map<Category, List<Product>> productsByCategory = allProducts.stream()
                .filter(Product::isHide)
                .collect(Collectors.groupingBy(Product::getCategory));
        productsByCategory.forEach((category, products) -> {
            List<Product> top3Products = products.stream()
                    .sorted(Comparator.comparingInt(Product::getOrder))
                    .limit(3)
                    .collect(Collectors.toList());
            productsByCategory.put(category, top3Products);
        });
        return productsByCategory;
    }

    public Map<Category, List<Product>> getTop6ProductsByCategory() {
        List<Product> allProducts = productRepository.findAll();
        Map<Category, List<Product>> productsByCategory = allProducts.stream()
                .filter(Product::isHide)
                .collect(Collectors.groupingBy(Product::getCategory));
        productsByCategory.forEach((category, products) -> {
            List<Product> top6Products = products.stream()
                    .sorted(Comparator.comparingInt(Product::getOrder))
                    .limit(6)
                    .collect(Collectors.toList());
            productsByCategory.put(category, top6Products);
        });
        return productsByCategory;
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }
    public Product updateProduct(Product product, String imagePath) {
        Product existingProduct = productRepository.findById((long) product.getId())
                .orElseThrow(() -> new IllegalStateException("Product with ID " + product.getId() + " does not exist."));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setNums(product.getNums());
        existingProduct.setDetail(product.getDetail());
        existingProduct.setCategory(product.getCategory());  existingProduct.setMeta(product.getMeta());
        existingProduct.setOrder(product.getOrder());
        existingProduct.setLink(product.getLink());
        existingProduct.setHide(product.isHide());

        if(imagePath != null){
            existingProduct.setImg1(imagePath);
        }
        return productRepository.saveAndFlush(existingProduct);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        productRepository.deleteById(id);
    }
}