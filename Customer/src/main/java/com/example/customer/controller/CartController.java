package com.example.customer.controller;

import com.example.library.dto.ConfigDTO;
import com.example.library.model.CartItem;
import com.example.library.model.Customer;
import com.example.library.model.Product;
import com.example.library.model.ShoppingCart;
import com.example.library.service.*;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class CartController {
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

    @GetMapping("/cart")
    public String cart(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        Set<CartItem> cartItems = new HashSet<>();
        ShoppingCart shoppingCart = shoppingCartService.findByCustomer(customer);
        if (shoppingCart == null) {
            model.addAttribute("check", "Chưa có sản phẩm nào được thêm vào giỏ hàng");
            cartItems = null;
        } else
            cartItems = shoppingCart.getCartItem();
        model.addAttribute("shoppingCart", cartItems);
        if (shoppingCart != null)
            model.addAttribute("cart", shoppingCart);
        else model.addAttribute("cart", new ShoppingCart());
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO", configDTO);
        return "cart";
    }

    @GetMapping("/add-to-cart")
    public ResponseEntity<String> addItemToCart(@RequestParam("id") Long id,
                                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                                Model model,
                                                Principal principal,
                                                HttpServletRequest httpServletRequest) {
        if (principal == null) {
            String redirectUrl = "login";

            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", redirectUrl);

            return new ResponseEntity<>(headers, HttpStatus.FOUND);
        }
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        Product product = productService.getProductById(id);
        ShoppingCart shoppingCart = shoppingCartService.addItemtoCart(product, quantity, customer);
//        model.addAttribute("shoppingCart", shoppingCart);
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO", configDTO);
//        return "redirect:" + httpServletRequest.getHeader("Referer") // khi return String, có tác dụng là load lại trang hiện tại, không đi đâu hết
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // giữ nguyên hiện trường (^.^) k load trang luôn
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.GET, params = "action=update")
    public String updateShoppingCart(@RequestParam(value = "quantity") int quantity,
                                     @RequestParam(value = "id") Long productId,
                                     Model model,
                                     Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            ConfigDTO configDTO = service.getConfigPresent();
            Product product = productService.getProductById(productId);
            ShoppingCart shoppingCart = shoppingCartService.updateCart(product, quantity, customer);
            model.addAttribute("shoppingCart", shoppingCart);
            model.addAttribute("configDTO", configDTO);

            return "redirect:/cart";
        }
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.GET, params = "action")
    public String deleteCart(@RequestParam("id") long productId,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            Customer customer = customerService.findByUsername(username);
            ConfigDTO configDTO = service.getConfigPresent();
            Product product = productService.getProductById(productId);
            ShoppingCart shoppingCart = shoppingCartService.deleteItem(product, customer);
            model.addAttribute("shoppingCart", "");
            model.addAttribute("configDTO", configDTO);

            return "redirect:/cart";
        }
    }

    @GetMapping("/delete-all-cartItem")
    public String deleteAll(Model model, Principal principal) {
        String username = principal.getName();
        Customer customer = customerService.findByUsername(username);
        ConfigDTO configDTO = service.getConfigPresent();
//            ShoppingCart shoppingCart = shoppingCartService.findByCustomer(customer.getId());
        ShoppingCart shoppingCart = shoppingCartService.deleteAllItem(customer);
        model.addAttribute("shoppingCart", shoppingCart);
        model.addAttribute("configDTO", configDTO);

        return "redirect:/cart";
    }
}
