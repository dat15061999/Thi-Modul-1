package com.example.demo1.controller;

import com.example.demo1.service.AuthorService;
import com.example.demo1.service.BookAuthorService;
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
    private BookAuthorService bookAuthorService;
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
            case "delete":
                delete(req,resp);
                break;
            case "update":
                showUpdate(req,resp);
                break;
            case "showRestore":
                showRestore(req,resp);
                break;
            case "restore":
                restore(req,resp);
                break;
            case "deleteAll":
                deleteAll(req,resp);
                break;
            default:
                showList(req,resp);
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookService.search(req));
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("book/index.jsp").forward(req, resp);

    }

    private void deleteAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.deleteALl(req);
        resp.sendRedirect("/book?action=showRestore&message=Deleted All");
    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.restore(req);
        resp.sendRedirect("/book?action=showRestore&message=Restored");
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookService.getBooks(true));
        req.setAttribute("message", req.getParameter("message"));
        req.setAttribute("categories", categoryService.getCategories());
        req.setAttribute("authors", authorService.findAll());
        req.setAttribute("bookAuthor", bookAuthorService.getBookAuthors());
        req.getRequestDispatcher("book/restore.jsp").forward(req, resp);
    }

    private void showUpdate(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.delete(req);
        resp.sendRedirect("/book?message=Deleted");
    }


    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", bookService.getBooks(false));
        req.setAttribute("message", req.getParameter("message"));
        req.setAttribute("categories", categoryService.getCategories());
        req.setAttribute("authors", authorService.findAll());
        req.setAttribute("bookAuthor", bookAuthorService.getBookAuthors());
        req.getRequestDispatcher("book/index.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", categoryService.getCategories());
        req.setAttribute("authors", authorService.findAll());
        req.getRequestDispatcher("book/create.jsp").forward(req, resp);
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
            case "update":
                update(req, resp);
                break;
            case "search":
                search(req,resp);
                break;
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.update(req);
        resp.sendRedirect("/book?message=Updated");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        bookService.create(req);
        resp.sendRedirect("/book?message=Created");
    }

    @Override
    public void init() {
        bookService = new BookService();
        categoryService = new CategoryService();
        authorService = new AuthorService();
        bookAuthorService = new BookAuthorService();
    }
}