package com.example.demo1.controller;


import com.example.demo1.model.enumration.EGender;
import com.example.demo1.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "studentController", value = "/student")
public class StudentController extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentService();
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
            case "showRestore":
                showRestore(req,resp);
                break;
            case "restore":
                restore(req,resp);
                break;
            case "delete":
                delete(req,resp);
                break;
            default:
                showList(req, resp);
        }

    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        studentService.restore(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/student?action=showRestore&message=Restored");
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        studentService.remove(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/student?message=Deleted");
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("students", studentService.getStudents(true));
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("restore.jsp").forward(req, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("genders", EGender.values());
        req.getRequestDispatcher("create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "create":
                create(req, resp);
                break;
            case "edit":
                //TBD;
        }

    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        studentService.addStudent(name, dob,gender);
        resp.sendRedirect("/student?message=Created");
    }


    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("students", studentService.getStudents(false));
        req.setAttribute("message", req.getParameter("message"));
        req.getRequestDispatcher("student.jsp").forward(req, resp);
    }
}