package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CategoryDTO {
    private Long categoryId;
    private String categoryName;
//    private boolean is_deleted;
//    private boolean is_activated;
    private Long numberOfProduct;
}
