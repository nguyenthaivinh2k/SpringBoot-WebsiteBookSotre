package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ConfigDTO {
    private String logo;
    private String phoneNumber;
    private String email;
    private String address;
    private String timeForWork;
    private String slide1;
    private String slide2;
}
