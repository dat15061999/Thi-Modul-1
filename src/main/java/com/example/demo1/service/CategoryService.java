package com.example.demo1.service;

import com.example.demo1.dao.CategoryDAO;
import com.example.demo1.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService() {
        categoryDAO = new CategoryDAO();
    }

    public List<Category> getCategories(){
        return categoryDAO.findAll();
    }

    public Category getCategory(int id){
        return categoryDAO.findById(id);
    }
}