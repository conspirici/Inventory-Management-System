package com.grocerflow.view.auth;

import com.grocerflow.controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SignUpView extends JFrame {

    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> roleComboBox;
    private JButton signUpButton;
    private JButton backButton;

    private AuthController authController;

    public SignUpView() {
        authController = new AuthController();

        setTitle("GrocerFlow - Sign Up");
        setSize(480, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        Font titleFont = new Font("Segoe UI", Font.BOLD, 20);
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 13);

        JLabel titleLabel = new JLabel("Create a New Account");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.DARK_GRAY);
        titleLabel.setBounds(120, 25, 300, 30);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(labelFont);
        usernameLabel.setBounds(50, 70, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(inputFont);
        usernameField.setBounds(180, 70, 200, 28);
        usernameField.setBackground(Color.WHITE);
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(labelFont);
        emailLabel.setBounds(50, 110, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setFont(inputFont);
        emailField.setBounds(180, 110, 200, 28);
        emailField.setBackground(Color.WHITE);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(labelFont);
        passwordLabel.setBounds(50, 150, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(inputFont);
        passwordField.setBounds(180, 150, 200, 28);
        passwordField.setBackground(Color.WHITE);
        add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(labelFont);
        confirmPasswordLabel.setBounds(50, 190, 130, 25);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(inputFont);
        confirmPasswordField.setBounds(180, 190, 200, 28);
        confirmPasswordField.setBackground(Color.WHITE);
        add(confirmPasswordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(labelFont);
        roleLabel.setBounds(50, 230, 100, 25);
        add(roleLabel);

        String[] roles = { "employee", "admin" };
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(inputFont);
        roleComboBox.setBounds(180, 230, 200, 28);
        roleComboBox.setBackground(Color.WHITE);
        add(roleComboBox);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(90, 300, 120, 35);
        signUpButton.setFont(labelFont);
        styleButton(signUpButton);
        add(signUpButton);

        backButton = new JButton("Back to Login");
        backButton.setBounds(230, 300, 140, 35);
        backButton.setFont(labelFont);
        styleButton(backButton);
        add(backButton);

        // Action Listeners
        signUpButton.addActionListener((ActionEvent e) -> handleSignUp());

        backButton.addActionListener(e -> {
            dispose();
            new LoginView();
        });
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.decode("#4A4A4A"));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#2E2E2E"));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#4A4A4A"));
            }
        });
    }

    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String result = authController.signUp(username, password, email, role);

        if (result.startsWith("success")) {
            JOptionPane.showMessageDialog(this, "Sign up successful! Await admin approval.", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new LoginView();
        } else {
            JOptionPane.showMessageDialog(this, result, "Sign Up Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new SignUpView();
    }
}
