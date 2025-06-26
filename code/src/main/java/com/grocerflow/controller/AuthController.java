package com.grocerflow.controller;

import com.grocerflow.model.User;
import com.grocerflow.model.dao.UserDAO;
import com.grocerflow.util.PasswordHasher;

public class AuthController {

    private final UserDAO userDAO;

    public AuthController() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password) {
    User user = userDAO.getUserByUsername(username);

    if (user == null || !user.getStatus().equals("approved")) {
        return null;
    }

    if (!PasswordHasher.verifyPassword(password, user.getPasswordHash())) {
        return null;
    }

    return user;
}


    public String signUp(String username, String password, String email, String role) {
        if (userDAO.getUserByUsername(username) != null) {
            return "Username already exists.";
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);
        user.setEmail(email);
        user.setRole(role); 
        user.setStatus("pending");

        boolean success = userDAO.createUser(user);
        return success ? "Account creation request sent." : "Failed to create account.";
    }

    public boolean approveUser(int userId) {
        return userDAO.updateUserStatus(userId, "approved");
    }

    public boolean rejectUser(int userId) {
        return userDAO.updateUserStatus(userId, "rejected");
    }
}
