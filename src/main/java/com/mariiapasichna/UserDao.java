package com.mariiapasichna;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Storage {
    private Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public UserDao() throws SQLException {
        connection = DriverManager.getConnection(Const.JDBC_URL, Const.USER, Const.PASSWORD);
        maybeCreateUsersTable();
    }

    private void maybeCreateUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users (\n" +
                    "_id uuid PRIMARY KEY,\n" +
                    "name varchar(100),\n" +
                    "age int\n" +
                    ");");
        }
    }

    @Override
    public void removeAll() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM users;");
        }
    }

    @Override
    public void removeUser(String userId) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String request = String.format("DELETE FROM users WHERE _id = '%s';", userId);
            statement.executeUpdate(request);
        }
    }

    @Override
    public void removeUserByName(String name) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String request = String.format("DELETE FROM users WHERE name = '%s';", name);
            statement.executeUpdate(request);
        }
    }

    @Override
    public void addUser(User user) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String request = String.format("INSERT INTO users VALUES ('%s', '%s', '%d');", user.getId(), user.getName(), user.getAge());
            statement.execute(request);
        }
    }

    @Override
    public void updateUser(User user) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String request = String.format("UPDATE users SET name ='%s', age = '%d' WHERE _id = '%s';", user.getName(), user.getAge(), user.getId());
            statement.executeUpdate(request);
        }
    }

    @Override
    public User getUser(String userId) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String request = String.format("SELECT * FROM users WHERE _id = '%s';", userId);
            ResultSet resultSet = statement.executeQuery(request);
            if (resultSet.next()) {
                String id = resultSet.getString("_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                return new User(id, name, age);
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String request = String.format("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                String id = resultSet.getString("_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                list.add(new User(id, name, age));
            }
        }
        return list;
    }
}