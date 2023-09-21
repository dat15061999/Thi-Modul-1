package com.example.demo1.service;

import com.example.demo1.model.Author;
import com.example.demo1.model.Category;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {
    private static final List<Author> authors;

    static {
        authors = new ArrayList<>();
        authors.add(new Author(1, "Thang"));
        authors.add(new Author(2, "Bao"));
    }
    public List<Author> findAll(){
        return authors;
    }

    public Author findById(int id){
        return authors.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }
}