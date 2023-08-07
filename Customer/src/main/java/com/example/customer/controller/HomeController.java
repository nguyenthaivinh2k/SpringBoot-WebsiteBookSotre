package com.example.customer.controller;

import com.example.library.dto.ConfigDTO;
import com.example.library.dto.ProductDTO;
import com.example.library.model.Category;
import com.example.library.service.ConfigService;
import com.example.library.service.ProductServiceImpl;
import com.example.library.service.interfacee.CatogeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    private CatogeryService catogeryService;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private ConfigService configService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String home(Model model,
                       Principal principal,
                       HttpSession httpSession) {
        if (principal != null) {
            httpSession.setAttribute("username", principal.getName());
        } else
            httpSession.setAttribute("username", "Tài khoản");

        return "redirect:/home";
    }

    @GetMapping(value = {"/home",""})
    public String index(Model model,Principal principal,HttpSession httpSession){
           if (principal != null) {
            httpSession.setAttribute("username", principal.getName());
        } else
            httpSession.setAttribute("username", "Tài khoản");
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        List<Category> categories = catogeryService.findAll();
        List<ProductDTO> productDTOList = productService.findAll();
        model.addAttribute("title", "Home Page");
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDTOList);
        return "index";
    }
}
