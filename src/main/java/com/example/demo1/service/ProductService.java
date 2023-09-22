package com.example.demo1.service;

import com.example.demo1.dao.ProductDAO;
import com.example.demo1.model.Category;
import com.example.demo1.model.Product;
import com.example.demo1.service.dto.Page;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static List<Product> products = new ArrayList<>();

    private static int idCurrent;

    private final ProductDAO productDAO;
    private final CategoryService categoryService;

    public ProductService() {
        productDAO = new ProductDAO();
        categoryService = new CategoryService();

    }


    public void create(HttpServletRequest req) throws SQLException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String description = req.getParameter("description");
        String idCategory = req.getParameter("category");
        Category category = categoryService.getCategory(Integer.parseInt(idCategory));
        Product product = new Product();
        product.setId(++idCurrent);
        product.setName(name);
        product.setPrice(Integer.parseInt(price));
        product.setDescription(description);
        product.setCategory(category);
        productDAO.insertProduct(product);
    }

    public Page<Product> getProducts(int page){
        return productDAO.findAll(page);
    }

    public void update(HttpServletRequest req,int page) throws SQLException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String description = req.getParameter("description");
        String idCategory = req.getParameter("category");
        Category category = categoryService.getCategory(Integer.parseInt(idCategory));
        List<Product> list = productDAO.findAll(page).getContent();
        for (var item:list) {
            if (item.getId() == id) {
                item.setName(name);
                item.setPrice(Integer.parseInt(price));
                item.setDescription(description);
                item.setCategory(category);
                productDAO.updateUser(item);
                break;
            }
        }
    }
}