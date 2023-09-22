package com.example.demo1.dao;

import com.example.demo1.model.Category;
import com.example.demo1.model.Product;
import com.example.demo1.service.ProductService;
import com.example.demo1.service.dto.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDAO  extends DatabaseConnection{

    public Page<Product> findAll(int page){
        Page<Product> result = new Page<>();
        result.setCurrentPage(page);
        List<Product> content = new ArrayList<>();
        String SELECT_ALL = "SELECT p.*, c.name category_name " +
        "FROM products p JOIN categories c on " +
                "c.id = p.category_id " +
                "LIMIT 5 OFFSET ?";
        String SELECT_COUNT = "SELECT COUNT(1) cnt FROM products";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {
            preparedStatement.setInt(1, (page-1) * 2);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                var product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getInt("price"));
                product.setCategory(new Category(rs.getInt("category_id"), rs.getString("category_name")));
                content.add(product);
            }
            result.setContent(content);
            PreparedStatement preparedStatementCount = connection.prepareStatement(SELECT_COUNT);
            var rsCount = preparedStatementCount.executeQuery();
            if(rsCount.next()){
                result.setTotalPage((int) Math.ceil((double) rsCount.getInt("cnt") /5));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return result;
    }

    public void insertProduct(Product product) throws SQLException {
        String INSERT_PRODUCTS_SQL = "INSERT INTO quanlysanpham.products (name, price, description, category_id) VALUES (?,?,?,?);";
        System.out.println(INSERT_PRODUCTS_SQL);
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTS_SQL)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, String.valueOf(product.getPrice()));
            preparedStatement.setString(3, product.getDescription());
            preparedStatement.setString(4, String.valueOf( product.getCategory().getId()));
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteUser(int id) throws SQLException {
        String DELETE_PRODUCT_SQL = "DELETE FROM PRODUCTS WHERE ID = ?";
        boolean rowDeleted;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateUser(Product product) throws SQLException {
        String UPDATE_PRODUCTS_SQL = "UPDATE products SET  name = ?, price = ?, description = ?, category_id = ? WHERE id = ?;";
        boolean rowUpdated;
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCTS_SQL);) {
            statement.setString(1, product.getName());
            statement.setString(2, String.valueOf(product.getPrice()));
            statement.setString(3, product.getDescription());
            statement.setString(4, String.valueOf( product.getCategory().getId()));
            statement.setInt(5, product.getId());
            System.out.println(statement);

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
}