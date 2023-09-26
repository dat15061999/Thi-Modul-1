package com.example.demo1.service;

import com.example.demo1.dao.ProductDAO;
import com.example.demo1.model.Product;
import com.example.demo1.service.dto.Page;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }


    public void create(Product product){
        productDAO.create(product);
    }

    public Page<Product> getProducts(int page, boolean isShowRestore, String search){
        return productDAO.findAll(page, isShowRestore, search);
    }

    public Product findById(int id){
        return productDAO.findById(id);
    }

    public void update(Product product, int id){
        product.setId(id);
        productDAO.update(product);
    }

    public void restore(String[] ids){
        for (var id : ids) {
            productDAO.restore(Integer.parseInt(id));
        }
    }

    public void delete(int id){
        productDAO.delete(id);
    }
}