package com.example.demo1.service;
import com.example.demo1.dao.UserDAO;
import com.example.demo1.model.User;
import com.example.demo1.service.dto.Page;
import java.sql.SQLException;

public class UserService {
    private final UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public Page<User> getUsers(int page, boolean isShowRestore, String search){
        return userDAO.selectAllUsers(page, isShowRestore, search);
    }
    public int nextIDUser(){
        return userDAO.selectAll();
    }
    public void create(User user) {
        userDAO.create(user);
    }
    public void edit(User user) throws SQLException {
        userDAO.updateUser(user);
    }

    public void delete(int id) throws SQLException {
        userDAO.deleteUser(id);
    }
    public void restore(String[] ids) throws SQLException {
        for (var id : ids) {
            userDAO.restoreUser(Integer.parseInt(id));
        }
    }
}
