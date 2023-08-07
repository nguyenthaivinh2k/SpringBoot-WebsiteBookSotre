package com.example.admin.controller;

import com.example.library.dto.ConfigDTO;
import com.example.library.model.Order;
import com.example.library.service.*;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ImageUpload imageUpload;
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private ConfigService service;
    @Autowired
    private OrderService orderService;
    @GetMapping("/orders")
    String order(Model model,Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO", configDTO);
        List<Order> orderList = orderService.findAll();
        model.addAttribute("orders",orderList);
        return "orders";
    }
}
