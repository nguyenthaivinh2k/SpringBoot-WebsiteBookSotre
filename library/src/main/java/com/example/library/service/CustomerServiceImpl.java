package com.example.library.service;

import com.example.library.dto.CustomerDTO;
import com.example.library.model.Customer;
import com.example.library.repository.CustomerRepository;
import com.example.library.repository.RoleRepository;
import com.example.library.service.interfacee.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    RoleRepository roleRepository;
    Sort desc = Sort.by(Sort.Direction.DESC, "customer_id");
    private BCryptPasswordEncoder passwordEncoder;

    //    Sort sortByIdAndStatus = Sort.by(
//            Sort.Order.desc("id"),
//            Sort.Order.desc("status")
//    );
    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setRoles(Arrays.asList(roleRepository.findByName("CUSTOMER")));
        Customer customerSave = customerRepository.save(customer);
        return mapperDTO(customerSave);
    }

    public CustomerDTO mapperDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setPassword(customer.getPassword());
        return customerDTO;
    }

    public boolean isExist(String email) {
        Customer userByEmail = customerRepository.findByUsername(email);
        if (userByEmail == null) return false;
        if (email.equalsIgnoreCase(userByEmail.getUsername()))
            return true;
        else return false;
    }

    public boolean checkSizeInput(int size, int minSize, int maxSize) {
        if (size < minSize || size > maxSize) return false;
        return true;
    }

    public Customer update(Customer customerDTO) {
        try {
            Customer customer = customerRepository.getById(customerDTO.getId());
            customer.setLastName(customerDTO.getLastName());
            customer.setFirstName(customerDTO.getFirstName());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customer.setUsername(customerDTO.getUsername());
            customer.setPassword(customer.getPassword());
            customer.setImage(customer.getImage());
            return customerRepository.save(customer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
