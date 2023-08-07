package com.example.customer.controller;

import com.example.library.dto.ConfigDTO;
import com.example.library.model.Customer;
import com.example.library.model.Order;
import com.example.library.service.*;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class AccountController {
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

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO", configDTO);
    }

    @GetMapping("my-account")
    public String myAccount(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        String phone = customer.getPhoneNumber();
        String name = customer.getFirstName() + " " + customer.getLastName();
        String email = customer.getUsername();
        model.addAttribute("customerId", customer.getId());
        model.addAttribute("name", name);
        model.addAttribute("phone", phone);
        model.addAttribute("email", email);
        return "my-account";
    }

    @GetMapping("my-orders")
    public String myOrders(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        List<Order> orders = customer.getOrders();
        model.addAttribute("orders", orders);
        return "my-orders";
    }

    @GetMapping("edit-my-account/{id}")
    public String editMyAccount(Principal principal, Model model, @PathVariable("id") Long id) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        model.addAttribute("customer", customer);
        return "edit-my-account";
    }

    @PostMapping("edit-my-account/{id}")
    public String updateMyAccount(@ModelAttribute("customer")Customer customer, Principal principal, RedirectAttributes attributes, Model model, @PathVariable("id") Long id) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
//        Customer customer2 = customerService.findByUsername(username);
        try {
            customerService.update(customer);
            attributes.addFlashAttribute("success", "cập nhật thành công");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/my-account";
    }
}
