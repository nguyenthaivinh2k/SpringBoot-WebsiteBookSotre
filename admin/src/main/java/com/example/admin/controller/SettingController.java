package com.example.admin.controller;

import com.example.library.dto.ConfigDTO;
import com.example.library.service.ConfigService;
import com.example.library.service.ProductServiceImpl;
import com.example.library.service.interfacee.CatogeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class SettingController {
    @Autowired
    private ConfigService service;
    @Autowired
    private CatogeryService catogeryService;
    @Autowired
    private ProductServiceImpl productService;
    @GetMapping("/config")
    public String config(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ADMIN"));
        if(!isAdmin){
            return "error-role";
        }
        ConfigDTO configDTO = service.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        return "config";
    }

    @PostMapping("/update-config")
    public String saveConfig(@ModelAttribute("configDTO")ConfigDTO configDTO, @RequestParam("imageLogo") MultipartFile imageLogo)
    {
        service.update(configDTO,imageLogo);

        return "redirect:/config";
    }
}
