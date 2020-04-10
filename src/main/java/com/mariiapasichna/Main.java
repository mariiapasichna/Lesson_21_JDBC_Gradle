package com.mariiapasichna;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            UserDao userDao = new UserDao();

            userDao.removeAll();

            userDao.addUser(new User(Helper.generateId(),"Ben",12));
            userDao.addUser(new User(Helper.generateId(),"Carl",45));
            userDao.addUser(new User(Helper.generateId(),"Bill",33));
            userDao.addUser(new User(Helper.generateId(),"Alex",76));
            userDao.addUser(new User(Helper.generateId(),"Anna",54));
            userDao.addUser(new User(Helper.generateId(),"Jon",34));
            userDao.addUser(new User(Helper.generateId(),"Den",44));

            System.out.println(userDao.getUser("7827db2e-e9ff-4457-9efe-3528f9e3d1e0"));

            userDao.removeUser("7827db2e-e9ff-4457-9efe-3528f9e3d1e0");
            System.out.println(userDao.getAllUsers());

            userDao.removeUserByName("Anna");
            System.out.println(userDao.getAllUsers());

            User user = userDao.getUser("617070b7-6289-4722-b212-a330e5975c6c");
            user.setName("Helen");
            user.setAge(100);
            userDao.updateUser(user);
            System.out.println(userDao.getAllUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}