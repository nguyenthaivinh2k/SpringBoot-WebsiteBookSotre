package com.example.customer.controller;

import com.example.library.dto.ConfigDTO;
import com.example.library.dto.CustomerDTO;
import com.example.library.model.Customer;
import com.example.library.service.ConfigService;
import com.example.library.service.CustomerServiceImpl;
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
public class AuthController {
    @Autowired
    private ConfigService configService;
    @Autowired
    CustomerServiceImpl customerService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String login(Model model) {
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        return "login";

    }

    @GetMapping("/register")
    public String register(Model model) {
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        model.addAttribute("customerDTO", new CustomerDTO());
        return "register";
    }

    @PostMapping("register-new")
    public String addNewAdmin(@Valid @ModelAttribute("customerDTO") CustomerDTO CustomerDTO,
                              BindingResult result,
                              Model model
    ) {
        ConfigDTO configDTO = configService.getConfigPresent();
        model.addAttribute("configDTO",configDTO);
        try {
            if (result.hasErrors()) {
//                model.addAttribute("customerDTO", CustomerDTO);
                return "register";
            }
            String username = CustomerDTO.getUsername();
            Customer customer = customerService.findByUsername(username);
            if (customer != null) {
                model.addAttribute("customerDTO", CustomerDTO);
                model.addAttribute("emailError", "email đã tồn tại");
                return "register";
            }
            if (CustomerDTO.getPassword().equals(CustomerDTO.getRepeatPassword())) {
                CustomerDTO.setPassword(passwordEncoder.encode(CustomerDTO.getPassword()));
                CustomerDTO customerDTOSave = customerService.save(CustomerDTO);
                model.addAttribute("success", "đăng ký thành công");
            } else {
                model.addAttribute("customerDTO", CustomerDTO);
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