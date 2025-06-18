package com.grocerflow.view.auth;

import com.grocerflow.controller.AuthController;
import com.grocerflow.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signUpButton;

    private AuthController authController;

    public LoginView() {
        authController = new AuthController();

        setTitle("GrocerFlow - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); // Absolute layout

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Login to GrocerFlow");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(100, 20, 200, 30);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 70, 180, 25);
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 110, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 180, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(80, 170, 100, 30);
        add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(200, 170, 100, 30);
        add(signUpButton);

        // Action Listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                new SignUpView(); // Open signup view (make sure it exists)
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        User loggedInUser = authController.login(username, password);

        if (loggedInUser != null) {
    JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
    dispose();

    if ("admin".equals(loggedInUser.getRole())) {
        new com.grocerflow.view.dashboard.AdminDashboardView(loggedInUser.getUserId());
    } else {
        new com.grocerflow.view.dashboard.EmployeeDashboardView(loggedInUser.getUserId());
    }
} else {
    JOptionPane.showMessageDialog(this, "Invalid login credentials or unapproved account.", "Login Failed", JOptionPane.ERROR_MESSAGE);
}
    }

    public static void main(String[] args) {
        new LoginView();
    }
}
