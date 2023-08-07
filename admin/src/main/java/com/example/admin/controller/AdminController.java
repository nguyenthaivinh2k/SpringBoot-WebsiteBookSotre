package com.example.admin.controller;

import com.example.library.service.*;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class AdminController {
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

    @GetMapping(value = {"/", "/index"})
    public String home(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ADMIN"));
//        boolean isAdminAndShipper = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .anyMatch(authority -> authority.equals("ADMIN") || authority.equals("SHIPPER"));
        model.addAttribute("title", "Home Page");
        session.setAttribute("roleName", principal.getName());
        model.addAttribute("numberOfProducts", productService.findAll().size());
        model.addAttribute("numberOfCategories", categoryService.findAll().size());
        model.addAttribute("numberOfCustomers",customerService.findAll().size());
        model.addAttribute("numberOfOrders",orderService.findAll().size());
        session.setAttribute("isAdmin", isAdmin);
        return "index";

    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }
}
