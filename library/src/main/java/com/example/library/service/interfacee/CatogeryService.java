package com.example.library.service.interfacee;

import com.example.library.dto.CategoryDTO;
import com.example.library.model.Category;

import java.util.List;

public interface CatogeryService {
    List<Category> findAll();
    Category save(Category category);
    Category findById(Long id);
    Category update(Category category);
    void deleteById(Long id);
    void enableById(Long id);
    List<Category> findAllByActive();

    boolean checkExistByName(String name);

    // customer
    List<CategoryDTO> getCategoryAndProduct();
}
