package com.hutech.Shop.Controllers;

import com.hutech.Shop.Services.BlogService;
import com.hutech.Shop.Services.MenuService;
import com.hutech.Shop.Services.ProductService;
import com.hutech.Shop.Services.SlideService;
import com.hutech.Shop.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/trang-chu")
@Controller
public class HomeController {
    private final ProductService productService;
    private final BlogService blogService;
    private final MenuService menuService;
    private final SlideService slideService;
    @GetMapping
    public String Home(Model model){
        List<Blog> blogs = blogService.findAllBlogs();
        List<Menu> menus = menuService.findAllMenu();
        List<Slide> slides = slideService.findAllSlide();
        Map<Category, List<Product>> categoryProducts =
                productService.getTop3ProductsByCategory();
        model.addAttribute("categoryProducts", categoryProducts);
        model.addAttribute("slides", slides);
        model.addAttribute("blogs", blogs);
        model.addAttribute("menus", menus);
        return "layout";
    }
}


