package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private long id;
    @Size(min = 3, max = 10, message = "độ dài trong khoảng từ 3 đến 10 ký tự")
    private String firstName;
    @Size(min = 3, max = 10, message = "độ dài trong khoảng từ 3 đến 10 ký tự")
    private String lastName;

    private String username;
    @Size(min = 3, max = 10, message = "độ dài trong khoảng từ 3 đến 10 ký tự")
    private String password;
    private String repeatPassword;


}
