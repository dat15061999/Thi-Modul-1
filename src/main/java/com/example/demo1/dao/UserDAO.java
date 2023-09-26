package com.example.demo1.dao;

import com.example.demo1.model.Role;
import com.example.demo1.model.User;
import com.example.demo1.model.UserRole;
import com.example.demo1.model.enumration.EGender;
import com.example.demo1.service.dto.Page;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DatabaseConnection{
    private final String SELECT_ALL_USERS = "SELECT u.*, GROUP_CONCAT(r.name) AS role_name " +
            "FROM users u " +
            "JOIN user_role ur ON ur.user_id = u.id " +
            "JOIN roles r ON r.id = ur.role_id " +
            "WHERE u.deleted = ? " +
            "GROUP BY u.id " +
            "HAVING (LOWER(u.firstname) LIKE ? OR LOWER(u.lastname) LIKE ? or LOWER(u.username) LIKE ? or LOWER(u.email) LIKE ? or LOWER(u.gender) LIKE ?) " +
            "LIMIT ? OFFSET ?";

    private final String INSERT_USERS_SQL = "INSERT INTO `quanlynguoidung`.`users` (`firstname`, `lastname`, `username`, `email`, `dob`, `gender`) VALUES (?, ?, ?, ?, ?, ?);";
    private final String DELETE_USERS_SQL = "UPDATE users set `deleted` = '1' where id = ? ;";
    private final String RESTORE_USERS_SQL = "UPDATE users set `deleted` = '0' where id = ? ;";
    private final String MAX_USERS_SQL = "SELECT MAX(ID) CNT FROM users;";
    private final String UPDATE_USERS_SQL = "UPDATE users SET `firstname` = ?, `lastname` = ?, `username` = ?, `email` = ?, `dob` = ?, gender = ? WHERE users.id = ?;";



    public Page<User> selectAllUsers(int page, boolean isShowRestore, String search) {
        var result = new Page<User>();
        final int TOTAL_ELEMENT = 3;
        result.setCurrentPage(page);
        var users = new ArrayList<User>();
        if(search == null){
            search = "";
        }
        search = "%" + search.toLowerCase() + "%";
        final var DELETED = isShowRestore ? 1 : 0;
        var SELECT_COUNT = "SELECT COUNT(1) cnt FROM users u JOIN user_role ur ON ur.user_id = u.id JOIN roles r ON r.id = ur.role_id WHERE u.deleted = ? and (LOWER(u.firstname) LIKE ? OR LOWER(u.lastname) LIKE ? or LOWER(u.username) LIKE ? or LOWER(u.email) LIKE ? or LOWER(u.gender) LIKE ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS)) {
            System.out.println(preparedStatement);
            preparedStatement.setInt(1,DELETED);
            preparedStatement.setString(2,search);
            preparedStatement.setString(3,search);
            preparedStatement.setString(4,search);
            preparedStatement.setString(5,search);
            preparedStatement.setString(6,search);
            preparedStatement.setInt(7,TOTAL_ELEMENT);
            preparedStatement.setInt(8,(page - 1) * TOTAL_ELEMENT);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUsername(rs.getString("username"));
                user.setEmail( rs.getString("email"));
                user.setDob(Date.valueOf(rs.getString("dob")));
                String roleID = rs.getString("role_name");
                List<UserRole> userRoles = new ArrayList<>();
                for (var role : roleID.split(",")) {
                    userRoles.add(new UserRole(user,new Role(role)));
                }
                user.setRole(userRoles);
                user.setGender(EGender.valueOf(rs.getString("gender")));



                users.add(user);
            }
                result.setContent(users);
            var preparedStatementCount = connection.prepareStatement(SELECT_COUNT);
            preparedStatementCount.setInt(1,DELETED );
            preparedStatementCount.setString(2,search);
            preparedStatementCount.setString(3,search);
            preparedStatementCount.setString(4,search);
            preparedStatementCount.setString(5,search);
            preparedStatementCount.setString(6,search);
            var rsCount = preparedStatementCount.executeQuery();
            if(rsCount.next()){
                result.setTotalPage((int) Math.ceil((double) rsCount.getInt("cnt") /TOTAL_ELEMENT));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public boolean deleteUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    public boolean restoreUser(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(RESTORE_USERS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public void create(User user) {
        System.out.println(INSERT_USERS_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
            preparedStatement.setString(1, user.getLastname());
            preparedStatement.setString(2, user.getFirstname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, String.valueOf(user.getDob()));
            preparedStatement.setString(6, user.getGender().toString());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateUser(User user) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
            statement.setString(1, user.getLastname());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getEmail());
            statement.setString(5, String.valueOf(user.getDob()));
            statement.setString(6, user.getGender().toString());
            statement.setString(7, String.valueOf(user.getId()));
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    public int selectAll() {
        try {
            Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(MAX_USERS_SQL);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return rs.getInt("cnt");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
