package com.example.library.service.interfacee;

import com.example.library.dto.CustomerDTO;
import com.example.library.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();

    Customer findByUsername(String username);
    CustomerDTO save(CustomerDTO customerDTO);
}
