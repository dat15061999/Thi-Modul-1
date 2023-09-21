package com.example.demo1.service;

import com.example.demo1.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static List<Product> products = new ArrayList<>();

    private static int idCurrent;

    public void create(Product product){
        product.setId(++idCurrent);
        products.add(product);
    }

    public List<Product> getProducts(){
        return products;
    }
}