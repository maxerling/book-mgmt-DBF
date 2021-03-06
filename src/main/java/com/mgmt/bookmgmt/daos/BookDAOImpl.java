package com.mgmt.bookmgmt.daos;

import com.mgmt.bookmgmt.models.Book;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Max Erling
 * Date: 2021-03-28
 * Copyright: MIT
 * Class: Java20B
 */
public class BookDAOImpl implements BookDAO {


    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection con = getConnection()) {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                Book temp = new Book(rs.getInt("id"),rs.getString("title"),rs.getInt("price"),rs.getString("author"));
                System.out.println(temp);
                books.add(temp);

            }

        } catch (SQLException sqlError) {
            System.out.println("SQL Error: " + sqlError.getMessage());
        }
        return books;
    }

    @Override
    public void createBook(Book book) {
        String sql = "INSERT INTO book(title,price,author) VALUES (?,?,?)";
        try (Connection con = getConnection()) {
            PreparedStatement prepStm = con.prepareStatement(sql);
            prepStm.setString(1,book.getTitle());
            prepStm.setInt(2,book.getPrice());
            prepStm.setString(1,book.getAuthor());
            prepStm.executeUpdate();
        } catch (SQLException sqlError) {
            System.out.println("SQL Error: " + sqlError.getMessage());
        }

    }

    @Override
    public Book readBook(int id) {
        Book temp = null;
        String sql = "SELECT * FROM book WHERE book.id = ?" ;
        try (Connection con = getConnection()) {
            PreparedStatement prepStm = con.prepareStatement(sql);
            prepStm.setInt(1,id);
            ResultSet rs = prepStm.executeQuery();
            if (rs.next()) {
                temp = new Book(rs.getInt("id"),rs.getString("title"),rs.getInt("price"),rs.getString("author"));
            }
        } catch (SQLException sqlError) {
            System.out.println("SQL Error: " + sqlError.getMessage());
        }
        return temp;
    }


    @Override
    public Book readBook(String title) {
        Book temp = null;
        String sql = "SELECT * FROM book WHERE book.title = ?" ;
        try (Connection con = getConnection()) {
            PreparedStatement prepStm = con.prepareStatement(sql);
            prepStm.setString(1,title);
            ResultSet rs = prepStm.executeQuery();
            if (rs.next()) {
                temp = new Book(rs.getInt("id"),rs.getString("title"),rs.getInt("price"),rs.getString("author"));
            }
        } catch (SQLException sqlError) {
            System.out.println("SQL Error: " + sqlError.getMessage());
        }
        return temp;
    }



    @Override
    public void updateBook(Book book) {
        String sql = "UPDATE book SET book.title = ?, book.price = ?, book.author = ?  WHERE book.id = ?";
        try (Connection con = getConnection()) {
            PreparedStatement prepStm = con.prepareStatement(sql);
            prepStm.setString(1,book.getTitle());
            prepStm.setInt(2,book.getPrice());
            prepStm.setString(3,book.getAuthor());
            prepStm.setInt(4,book.getId());
            prepStm.execute();

        } catch (SQLException sqlError) {
            System.out.println("SQL Error: " + sqlError.getMessage());
        }

    }

    @Override
    public void deleteBook(String title) {
        String sql = "DELETE FROM book WHERE book.title = ?";
        try (Connection con = getConnection()) {
            PreparedStatement prepStm = con.prepareStatement(sql);
            prepStm.setString(1,title);
            prepStm.execute();

        } catch (SQLException sqlError) {
            System.out.println("SQL Error: " + sqlError.getMessage());
        }

    }


    public Connection getConnection() throws SQLException {
        String user = "";
        String pass = "";
        String connection = "";
        /*try {
            Properties prop = new Properties();
            FileInputStream in = new FileInputStream("C:\\Users\\m\\Desktop\\Programming\\2021\\book-mgmt\\src\\main\\resources\\app.properties");

            prop.load(in);
            user = prop.getProperty("dbuser");
            pass = prop.getProperty("dbpass");
            connection = prop.getProperty("connection");

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }*/


        return DriverManager.getConnection("jdbc:mysql://localhost:3306/management?useSSL=false","root","root");

    }
}
