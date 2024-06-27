package com.hutech.Shop.Controllers;

import com.hutech.Shop.Services.CategoryService;
import com.hutech.Shop.Services.MenuService;
import com.hutech.Shop.Services.ProductService;
import com.hutech.Shop.model.Category;
import com.hutech.Shop.model.Menu;
import com.hutech.Shop.model.Product;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final CategoryService categoryService;
    private final MenuService menuService;
    private final ProductService productService;

    @GetMapping
    public String showProductList(Model model) {
        List<Menu> menus = menuService.findAllMenu();
        Map<Category, List<Product>> categoryProducts =
                productService.getTop6ProductsByCategory();
        model.addAttribute("categoryProducts", categoryProducts);
        model.addAttribute("menus", menus);
        return "/products/product-list";
    }
    @GetMapping("/detail/{link}")
    public String productDetail(@PathVariable String link, Model model) {
        Optional<Product> productOpt = productService.getProductByLink(link);
        if (productOpt.isPresent()) {
            model.addAttribute("product", productOpt.get());
            addCommonAttributes(model);
            return "/products/product-detail";
        } else {
            return "error"; // Handle the case when the product is not found
        }
    }
    @GetMapping("/{category}")
    public String ProductCategory(Model model,
                                  @PathVariable String category,
                                  @RequestParam(name = "page", defaultValue = "0") int page) {
        Category cat = categoryService.findByLink(category);
        if (cat == null) {
            return "error"; // Xử lý khi không tìm thấy danh mục
        }
        Long categoryId = cat.getId();
        List<Product> productsForCategory =
                productService.getProductsByCategoryId(categoryId);
        model.addAttribute("categoryName", cat.getName());
        model.addAttribute("productsForCategory", productsForCategory);
        addCommonAttributes(model);
        return "products/product";
    }
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "/products/add-product";
    }

    private void addCommonAttributes(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("menus", menuService.findAllMenu());
    }
}