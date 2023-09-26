package com.example.demo1.controller;

import com.example.demo1.service.AuthorService;
import com.example.demo1.service.BookService;
import com.example.demo1.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "bookController", urlPatterns = "/book")
public class BookController extends HttpServlet {
    private BookService bookService;
    private CategoryService categoryService;
    private AuthorService authorService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action){
            case "create":
                showCreate(req,resp);
                break;
            default:
                showList(req,resp);
        }
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookService.getBooks());
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("book/index.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", categoryService.getCategories());
        req.setAttribute("authors", authorService.findAll());
        req.getRequestDispatcher("user/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action == null){
            action = "";
        }
        switch (action) {
            case "create":
                create(req, resp);
                break;
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.create(req);
        resp.sendRedirect("/book");
    }

    @Override
    public void init() {
        bookService = new BookService();
        categoryService = new CategoryService();
        authorService = new AuthorService();
    }
}