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
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null); // Absolute layout
        getContentPane().setBackground(new Color(240, 240, 240)); // light gray

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 13);

        JLabel titleLabel = new JLabel("Welcome to GrocerFlow");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(new Color(40, 40, 40));
        titleLabel.setBounds(80, 30, 250, 30);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setBounds(50, 90, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(inputFont);
        usernameField.setBounds(150, 90, 180, 28);
        usernameField.setBackground(Color.WHITE);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(50, 135, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(inputFont);
        passwordField.setBounds(150, 135, 180, 28);
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(80, 200, 100, 35);
        loginButton.setFont(labelFont);
        styleButton(loginButton);
        add(loginButton);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(200, 200, 100, 35);
        signUpButton.setFont(labelFont);
        styleButton(signUpButton);
        add(signUpButton);

        // Action Listeners
        loginButton.addActionListener(e -> handleLogin());

        signUpButton.addActionListener(e -> {
            dispose();
            new SignUpView();
        });
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(74, 74, 74)); // Dark Gray
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(46, 46, 46)); // Slightly darker
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(74, 74, 74));
            }
        });
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Incomplete Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        User loggedInUser = authController.login(username, password);

        if (loggedInUser != null) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Welcome", JOptionPane.INFORMATION_MESSAGE);
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
