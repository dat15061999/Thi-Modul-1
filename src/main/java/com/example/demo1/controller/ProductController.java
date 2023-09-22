package com.example.demo1.controller;

import com.example.demo1.model.Category;
import com.example.demo1.model.Product;
import com.example.demo1.service.CategoryService;
import com.example.demo1.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "productController", urlPatterns = "/product")
public class ProductController extends HttpServlet {
    private ProductService productService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
        categoryService = new CategoryService();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                showCreate(req, resp);
                break;
            default:
                showList(req, resp);
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageString = req.getParameter("page");
        if (pageString == null) {
            pageString = "1";
        }
        req.setAttribute("page", productService.getProducts(Integer.parseInt(pageString)));
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("product/index.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", categoryService.getCategories());
        req.getRequestDispatcher("product/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            if (action.equals("create")) {
                create(req, resp);
            }
            if (action.equals("edit")) {
                edit(req, resp);
            }
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

}

    private void edit(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String pageString = req.getParameter("page");
        if (pageString == null) {
            pageString = "1";
        }
        productService.update(req, Integer.parseInt(pageString));
        resp.sendRedirect("/product?message=Updated");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        productService.create(req);
        resp.sendRedirect("/product?message=Created");
    }


}