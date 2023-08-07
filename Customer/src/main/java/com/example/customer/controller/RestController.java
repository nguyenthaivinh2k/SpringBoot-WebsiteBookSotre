package com.example.customer.controller;

import com.example.library.dto.ConfigDTO;
import com.example.library.model.CartItem;
import com.example.library.model.Customer;
import com.example.library.model.Product;
import com.example.library.model.ShoppingCart;
import com.example.library.service.*;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Set;

@org.springframework.web.bind.annotation.RestController
public class RestController {

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

    @GetMapping("/check-email")
    public String checkDuplicateEmail(@RequestParam("email") String email) {
        if (!customerService.isExist(email))
            return "OK";
        else return "notOK";
    }

    @GetMapping("/check-size")
    public String checkSize(@RequestParam("size") int size) {
        if (customerService.checkSizeInput(size, 3, 10))
            return "OK";
        else return "notOK";
    }

    @RequestMapping(value = "/update-cart1", method = RequestMethod.GET)
    public String updateShoppingCart1(@RequestParam(value = "quantityValue") String quantity,
                                      @RequestParam(value = "idValue") Long productId,
                                      Model model,
                                      Principal principal) {
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        Product product = productService.getProductById(productId);
        ShoppingCart shoppingCart = shoppingCartService.updateCart(product, Integer.parseInt(quantity), customer);
        Set<CartItem> cartItems = shoppingCart.getCartItem();
        CartItem item = shoppingCartService.findCartItem(cartItems, product.getId());
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO", configDTO);
        model.addAttribute("shoppingCart", shoppingCart);
        String formattedValue = productService.formatPrice(item.getTotalPrice());
        return formattedValue;
    }

    @RequestMapping(value = "/update-cart2", method = RequestMethod.GET)
    public String updateShoppingCart2(
                                      Model model,
                                      Principal principal) {
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ConfigDTO configDTO = service.getConfigPresent();
        ShoppingCart shoppingCart = shoppingCartService.findByCustomer(customer);
        String formattedValue = productService.formatPrice(shoppingCart.getTotalPrice());
        model.addAttribute("configDTO", configDTO);
        return formattedValue;
    }
}

