package com.example.admin.controller;

import com.example.library.dto.AdminDTO;
import com.example.library.model.Admin;
import com.example.library.service.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AdminServiceImpl adminService;




    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("adminDTO", new AdminDTO());
        model.addAttribute("title", "Register");
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassForm(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgot-password";
    }

    @PostMapping("register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDTO") AdminDTO adminDTO,
                              BindingResult result,
                              Model model
    ) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDTO", adminDTO);
                return "register";
            }
            String username = adminDTO.getUsername();
            Admin admin = adminService.findByUsername(username);
            if (admin != null) {
                model.addAttribute("adminDTO", adminDTO);
                model.addAttribute("emailError", "email đã tồn tại");
                return "register";
            }
            if (adminDTO.getPassword().equals(adminDTO.getRepeatPassword())) {
                adminDTO.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
                adminService.save(adminDTO);
                model.addAttribute("adminDTO", adminDTO);
                model.addAttribute("success", "đăng ký thành công");
            } else {
                model.addAttribute("adminDTO", adminDTO);
                model.addAttribute("passwordError", "nhập lại mật khẩu không trùng khớp");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("exception", "lỗi hệ thống");
        }
        return "register";
    }
}
