package com.example.demo1.service;
import com.example.demo1.dao.UserRoleDAO;
import com.example.demo1.model.UserRole;

public class UserRoleService {
    private final UserRoleDAO userRoleDAO;

    public UserRoleService() {
        userRoleDAO = new UserRoleDAO();
    }


    public UserRole create(UserRole userRole) {
        userRoleDAO.createUserRole(userRole);
        return userRole;
    }
    public void delete(int id) {
        userRoleDAO.delete(id);
    }
}
