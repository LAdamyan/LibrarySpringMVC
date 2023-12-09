package com.epam.libraryspringmvc.manager.impl;

import com.epam.libraryspringmvc.db.DBConnectionProvider;
import com.epam.libraryspringmvc.manager.BookManager;
import com.epam.libraryspringmvc.model.Book;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component("bookManager")
@Repository
public class BookManagerImpl implements BookManager<Integer, Book> {


    @Qualifier("dbConnectionProvider")
    private DBConnectionProvider dbConnectionProvider;

    public BookManagerImpl(DBConnectionProvider dbConnectionProvider) {
        this.dbConnectionProvider = dbConnectionProvider;
    }

    private  Connection connection;

    @Override
    public Book getById(Integer id) {
        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book where id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookName(resultSet.getString("book_name"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setUserId(resultSet.getInt("user_id"));
                return book;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Book> getAll() {
        List<Book> books = new ArrayList<>();
        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookName(resultSet.getString("book_name"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setUserId(resultSet.getInt("user_id"));
                book.setUserId(resultSet.getInt("user_id"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public void save(Book book) {
        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO book(book_name, author_name, user_id) VALUES(?,?,?)");
            preparedStatement.setString(1, book.getBookName());
            preparedStatement.setString(2, book.getAuthorName());
            preparedStatement.setInt(3, book.getUserId());
            int execute = preparedStatement.executeUpdate();
            System.out.println(execute);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Book book) {

        if (book != null) {
            try {
                connection = dbConnectionProvider.dataSource().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE book SET book_name=?, author_name=?, user_id=? WHERE id=?"
                );
                preparedStatement.setString(1, book.getBookName());
                preparedStatement.setString(2, book.getAuthorName());
                preparedStatement.setInt(3, book.getUserId());
                preparedStatement.setInt(4, book.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM book WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Book> getAllUnassignedBooks() {

        List<Book> books = new ArrayList<>();
        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book where user_id is null");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookName(resultSet.getString("book_name"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setUserId(resultSet.getInt("user_id"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getAllAssignedBooks() {
        List<Book>books = new ArrayList<>();
        try {
            connection = dbConnectionProvider.dataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM book where user_id is NOT NULL");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getInt("id"));
                book.setBookName(resultSet.getString("book_name"));
                book.setAuthorName(resultSet.getString("author_name"));
                book.setUserId(resultSet.getInt("user_id"));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return books;

    }
}
