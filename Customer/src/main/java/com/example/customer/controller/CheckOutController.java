package com.example.customer.controller;

import com.example.library.dto.ConfigDTO;
import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.model.ShoppingCart;
import com.example.library.service.*;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class CheckOutController {
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

    @GetMapping("/check-out")
    String checkOut(Model model, Principal principal) {
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO", configDTO);
        Customer customer = customerService.findByUsername(principal.getName());
        model.addAttribute("customer", customer);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        model.addAttribute("cart", shoppingCart);
        return "checkout";
    }
    @GetMapping("/order")
    String order(Model model,Principal principal) {
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO", configDTO);
        Customer customer = customerService.findByUsername(principal.getName());
        List<Order> orderList = customer.getOrders();
        model.addAttribute("orders",orderList);
        return "order";
    }
    @GetMapping("/do-check-out")
    String doCheckOut(Model model,Principal principal) {
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO", configDTO);
        Customer customer = customerService.findByUsername(principal.getName());
        orderService.saveOder(customer.getShoppingCart());
        return "docheckout";
    }
}
