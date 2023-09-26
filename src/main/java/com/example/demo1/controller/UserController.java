package com.example.demo1.controller;

import com.example.demo1.model.Role;
import com.example.demo1.model.User;
import com.example.demo1.model.UserRole;
import com.example.demo1.model.enumration.EGender;
import com.example.demo1.service.RoleService;
import com.example.demo1.service.UserRoleService;
import com.example.demo1.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@WebServlet(name = "userController", urlPatterns = "/user")
public class UserController extends HttpServlet {
    private UserService userService;
    private RoleService roleService;
    private UserRoleService userRoleService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        roleService = new RoleService();
        userRoleService = new UserRoleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showCreate(req, resp);
                    break;
                case "restore":
                    showRestore(req, resp);
                    break;
                case "delete":
                    delete(req, resp);
                    break;
                default:
                    showList(req, resp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        userService.delete(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/user?message=Deleted");
    }

    private void showRestore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req, true, resp);
    }

    private void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", roleService.getAllRoles());
        req.setAttribute("genders", EGender.values());
        req.getRequestDispatcher("user/create.jsp").forward(req, resp);
    }

    private void showList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        showTable(req, false, resp);
    }

    private void showTable(HttpServletRequest req, boolean isShowRestore, HttpServletResponse resp) throws ServletException, IOException {
        String pageString = req.getParameter("page");
        if (pageString == null) {
            pageString = "1";
        }
        req.setAttribute("page", userService.getUsers(Integer.parseInt(pageString), isShowRestore, req.getParameter("search")));
        req.setAttribute("isShowRestore", isShowRestore);
        req.setAttribute("roles", roleService.getAllRoles());
        req.setAttribute("genders", EGender.values());
        req.setAttribute("message", req.getParameter("message"));
        try {
            req.getRequestDispatcher("user/index.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User();
        user.setId(userService.nextIDUser()+1);
        showTable(user,req);
        userService.create(user);
        resp.sendRedirect("/user?message=Created");
    }
    private void showTable(User user, HttpServletRequest req) {
        List<Role> listRole = Arrays.stream(req.getParameterValues("role")).map(Integer::parseInt)
                .map(role-> roleService.getRole(role)).toList();
        List<UserRole> userRoles = new ArrayList<>();
        for (var role : listRole) {
            var userRole = userRoleService.create(new UserRole(user,new Role(role.getId(),role.getName())));
            userRoles.add(userRole);
        }
        user.setFirstname(req.getParameter("firstname"));
        user.setLastname(req.getParameter("lastname"));
        user.setUsername(req.getParameter("lastname"));
        user.setEmail(req.getParameter("email"));
        user.setDob(Date.valueOf(req.getParameter("dob")));
        user.setRole(userRoles);
        user.setGender(EGender.valueOf(req.getParameter("gender")));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    create(req, resp);
                    break;
                case "edit":
                    update(req, resp);
                    break;
                case "restore":
                    restore(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void restore(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String[] check = req.getParameterValues("restore");
        if (check != null) {
            userService.restore(check);
        }
        resp.sendRedirect("/user?message=Restored");


    }

    private void update(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        User user = new User();
        int idUser = Integer.parseInt(req.getParameter("id"));
        userRoleService.delete(idUser);
        user.setId(idUser);
        showTable(user,req);
        userService.edit(user);
        resp.sendRedirect("/user?message=Updated");
    }


}
