package com.example.library.service;

import com.example.library.dto.CategoryDTO;
import com.example.library.model.Category;
import com.example.library.repository.CategoryRepository;
import com.example.library.service.interfacee.CatogeryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CatogeryService {
    @Autowired
    CategoryRepository repo;

    @Override
    public List<Category> findAll() {
        return repo.findAll();
    }

    @Override
    public Category save(Category category) {
        try {
            Category categorySave = new Category(category.getName());
            return repo.save(categorySave);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Category findById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = null;
        try {
            categoryUpdate = repo.findById(category.getId()).get();
            categoryUpdate.setName(category.getName());
//            categoryUpdate.set_deleted(category.is_deleted());
//            categoryUpdate.set_activated(category.is_activated());
            return repo.save(categoryUpdate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repo.save(categoryUpdate);
    }

    @Override
    public void deleteById(Long id) {
        Category category = repo.getById(id);
        category.set_deleted(true);
        category.set_activated(false);
        repo.save(category);
    }

    @Override
    public void enableById(Long id) {
        Category category = repo.getById(id);
        category.set_deleted(false);
        category.set_activated(true);
        repo.save(category);
    }

    @Override
    public List<Category> findAllByActive() {
        return repo.findAllByActive();
    }

    @Override
    public boolean checkExistByName(String name) {
        List<Category> categories = repo.findAll();
        for (Category c : categories
        ) {
            if (c.getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    @Override
    public List<CategoryDTO> getCategoryAndProduct() {
        return repo.getCategoryAndProduct();
    }

}
