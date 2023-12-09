package com.epam.libraryspringmvc.manager.impl;

import com.epam.libraryspringmvc.db.DBConnectionProvider;
import com.epam.libraryspringmvc.manager.UserManager;
import com.epam.libraryspringmvc.model.User;
import com.epam.libraryspringmvc.model.UserRole;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component("userManager")
@Repository
public class UserManagerImpl implements UserManager<Integer, User> {


    @Qualifier("dbConnectionProvider")
    private DBConnectionProvider dbConnectionProvider;

    public UserManagerImpl(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
    }
    private Connection connection;


    @Override
    public User getById(Integer id) {

        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("user_role")));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setUserRole( UserRole.valueOf(resultSet.getString("user_role")));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User user) {

        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users(name, last_name, email, password) VALUES(?,?,?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            int execute = preparedStatement.executeUpdate();
            System.out.println(execute);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {

        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE users SET name=?, last_name=?, email=? where id=?;");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setInt(4, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Integer id) {
        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User getByEmailAndPassword(String email, String password) {

        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users where email=? and password=?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("user_role")));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Given user with email: %s and password %s not found", email, password));
        }
        return null;
    }
    public void setDbConnectionProvider(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
    }
}
