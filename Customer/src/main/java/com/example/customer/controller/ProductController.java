package com.example.customer.controller;

import com.example.library.dto.CategoryDTO;
import com.example.library.dto.ConfigDTO;
import com.example.library.model.Category;
import com.example.library.model.Product;
import com.example.library.service.CategoryServiceImpl;
import com.example.library.service.ConfigService;
import com.example.library.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ConfigService configService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/products")
    public String products(Model model){
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        List<CategoryDTO> categoryDTOList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getAllProducts();
//        List<Product> listViewProducts = productService.listViewProducts();
//        model.addAttribute("viewProducts",listViewProducts);
        model.addAttribute("categories",categoryDTOList);
        model.addAttribute("products",products);

        return "shop";
    }
    @GetMapping("product-detail/{id}")
    public String detailProduct(@PathVariable("id") Long id, Model model) {
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        return "product-detail";
    }
    @GetMapping("find-product/{id}")
    public String productDetail(@PathVariable("id") Long id,
                                Model model){
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        Product product = productService.getProductById(id);
        List<Product> reletedProducts = productService.getReletedProducts(product.getCategory().getId());
        model.addAttribute("reletedProducts",reletedProducts);
        model.addAttribute("product",product);
        return "product-detail";
    }
    @GetMapping("products-in-category/{id}")
    public String productInCategory(@PathVariable("id")Long categoryId, Model model){
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        Category category = categoryService.findById(categoryId);
        List<CategoryDTO> categoryDTOList = categoryService.getCategoryAndProduct();
        List<Product> products = productService.getProductsInCategory(categoryId);
//        List<Product> listViewProducts = productService.listViewProducts();
//        model.addAttribute("viewProducts",listViewProducts);
        model.addAttribute("category",category);
        model.addAttribute("products",products);
        model.addAttribute("categories",categoryDTOList);
        return "products-in-category";
    }
    @GetMapping("/search-result")
    public String searchProduct(
                                @RequestParam("keyWord") String keyWord,
                                Model model) {
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        List<CategoryDTO> categoryDTOList = categoryService.getCategoryAndProduct();
        Page<Product> page = productService.serachProduct(1, keyWord);
        List<Product> productList = page.getContent();
        model.addAttribute("products", productList);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("products",productList);
        model.addAttribute("categories",categoryDTOList);
        return "result-products";
    }
    @GetMapping("high-price")
    public String filterHighPrice(Model model){
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        List<Product> products = productService.filterHightPrice();
        List<Category> categories = categoryService.findAllByActive();
        List<CategoryDTO> categoryDTOList = categoryService.getCategoryAndProduct();
        model.addAttribute("products", products);
        model.addAttribute("categories",categories);
        model.addAttribute("categoryDTOList",categoryDTOList);
        return "filter-high-price";
    }
    @GetMapping("low-price")
    public String filterLowPrice(Model model){
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        List<Product> products = productService.filterLowPrice();
        List<Category> categories = categoryService.findAllByActive();
        List<CategoryDTO> categoryDTOList = categoryService.getCategoryAndProduct();
        model.addAttribute("categories",categories);
        model.addAttribute("products", products);
        model.addAttribute("categories",categories);
        model.addAttribute("categoryDTOList",categoryDTOList);
        return "filter-low-price";
    }
}
