package com.grocerflow.controller;

import com.grocerflow.model.User;
import com.grocerflow.model.dao.UserDAO;

import java.util.List;

public class UserController {

    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAO();
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public List<User> getPendingUsers() {
        return userDAO.getUsersByStatus("pending");
    }

    public boolean approveUser(int userId) {
        return userDAO.updateUserStatus(userId, "approved");
    }

    public boolean rejectUser(int userId) {
        return userDAO.updateUserStatus(userId, "rejected");
    }

    public boolean deleteUser(int userId) {
        return userDAO.deleteUser(userId);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public User getUserById(int userId) {
        return userDAO.getUserById(userId);
    }
}
