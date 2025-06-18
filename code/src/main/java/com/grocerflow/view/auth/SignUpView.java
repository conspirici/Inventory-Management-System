package com.grocerflow.view.auth;

import com.grocerflow.controller.AuthController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(245, 245, 245));

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel titleLabel = new JLabel("Create a New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(120, 20, 250, 30);
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 70, 100, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(160, 70, 200, 25);
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 110, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(160, 110, 200, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(160, 150, 200, 25);
        add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(50, 190, 130, 25);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(190, 190, 170, 25);
        add(confirmPasswordField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(50, 230, 100, 25);
        add(roleLabel);

        String[] roles = { "employee", "admin" };
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(160, 230, 200, 25);
        add(roleComboBox);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(90, 290, 120, 30);
        add(signUpButton);

        backButton = new JButton("Back to Login");
        backButton.setBounds(230, 290, 140, 30);
        add(backButton);

        // Action Listeners
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });

        backButton.addActionListener(e -> {
            dispose();
            new LoginView();
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
