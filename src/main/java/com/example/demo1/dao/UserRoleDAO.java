package com.example.demo1.dao;

import com.example.demo1.model.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRoleDAO extends DatabaseConnection{
    String INSERT_USER_ROLE_SQL = "INSERT INTO `quanlynguoidung`.`user_role` (`role_id`, `user_id`) VALUES (?, ?);";
    String DELETE_USER_ROLE_SQL = "DELETE FROM user_role WHERE user_id = ?";
    public void createUserRole(UserRole userRole) {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE_SQL)) {
            preparedStatement.setInt(1, userRole.getRole().getId());
            preparedStatement.setInt(2, userRole.getUser().getId());            
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int id) {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER_ROLE_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rowDeleted;
    }
}
